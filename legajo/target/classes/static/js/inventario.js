// /js/inventario.js
// CRUD para inventario.html (usuario)
const API = '/api/libros';

// Obtener usuario actual y cargar su inventario
async function obtenerUsuarioActual() {
  try {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
      window.location.href = '/login.html';
      return null;
    }
    const res = await fetch('/api/auth/me', {
      headers: { 'Authorization': 'Bearer ' + token }
    });
    if (!res.ok) {
      localStorage.removeItem('jwtToken');
      window.location.href = '/login.html';
      return null;
    }
    return await res.json();
  } catch (e) {
    console.error('Error obteniendo usuario actual:', e);
    return null;
  }
}

async function cargarInventario() {
  const grid = document.querySelector('.grid-inventario');
  if (!grid) return;
  grid.innerHTML = '';
  
  try {
    // Obtener usuario actual
    const usuario = await obtenerUsuarioActual();
    if (!usuario) return;
    
    // Cargar todos los libros
    const res = await fetch(API);
    if (!res.ok) throw new Error('Error al cargar libros');
    const libros = await res.json();
    
    // Filtrar solo los libros que pertenecen al usuario actual
    const misLibros = libros.filter(l => l.usuarioPropietarioId === usuario.idUsuario);
    
    if (!misLibros.length) {
      grid.innerHTML = '<p style="grid-column: 1/-1; text-align: center; padding: 40px;">No tienes libros en tu inventario.</p>';
      return;
    }
    
    misLibros.forEach(libro => {
      const item = document.createElement('div');
      item.className = 'item-inventario';
      const libroId = libro.idLibro || libro.id || '';

      item.innerHTML = `
        <img src="${libro.urlImagen || '/imgs/libro_de_la_selva.jpg'}" alt="Libro">
        <h3>${libro.titulo || ''}</h3>
        <h4>${libro.autor || ''}</h4>
        <div class="estrellas">★★★★★</div>
        <p class="descripcion">${libro.sinopsis || ''}</p>
        <button class="btn-verde" onclick="verLibro('${libroId}')"><i class="fas fa-eye"></i> Ver</button>
        <button class="btn-amarillo" onclick="editarLibro('${libroId}')"><i class="fas fa-edit"></i> Editar</button>
        <button class="btn-rojo" onclick="eliminarLibro('${libroId}')"><i class="fas fa-trash"></i> Eliminar</button>
      `;

      grid.appendChild(item);
    });

  } catch (e) {
    console.error('Error cargando inventario:', e);
    grid.innerHTML = '<p style="grid-column: 1/-1; text-align: center; padding: 40px;">Error cargando tu inventario.</p>';
  }
}

async function eliminarLibro(id) {

  // 🔥 Reemplazo confirm() por SweetAlert2
  const result = await Swal.fire({
    title: "¿Eliminar libro?",
    text: "Esta acción no se puede deshacer.",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Sí, eliminar",
    cancelButtonText: "Cancelar"
  });

  if (!result.isConfirmed) return;

  const token = localStorage.getItem('jwtToken');
  const headers = {};

  if (token) {
    headers['Authorization'] = 'Bearer ' + token;
  }

  const res = await fetch(`${API}/${id}`, { 
    method: 'DELETE',
    headers: headers
  });

  if (res.ok) {
    Swal.fire({
      icon: "success",
      title: "Eliminado",
      text: "El libro ha sido eliminado correctamente",
      timer: 1800,
      showConfirmButton: false
    });
    cargarInventario();
  } else {
    Swal.fire({
      icon: "error",
      title: "Error",
      text: "No se pudo eliminar el libro."
    });
  }
}

// Modal para mostrar detalles del libro
const modal = document.getElementById('modal');
const closeModal = document.getElementById('closeModal');

if (closeModal) {
  closeModal.addEventListener('click', () => {
    if (modal) modal.style.display = 'none';
  });
}

window.addEventListener('click', (event) => {
  if (modal && event.target === modal) {
    modal.style.display = 'none';
  }
});

async function verLibro(id) {
  try {
    const res = await fetch(`${API}/${id}`);
    if (!res.ok) throw new Error('Error al cargar libro');

    const libro = await res.json();
    
    // Rellenar modal con datos del libro
    document.getElementById('modalImg').src = libro.urlImagen || '/imgs/default-book.jpg';
    document.getElementById('modalTitulo').textContent = libro.titulo || '';
    document.getElementById('modalAutor').textContent = libro.autor || '';
    document.getElementById('modalDescripcion').textContent = libro.sinopsis || '';
    
    // Añadir botón de solicitar intercambio dentro del modal (si no existe)
    let acciones = document.getElementById('modalAcciones');
    if (!acciones) {
      acciones = document.createElement('div');
      acciones.id = 'modalAcciones';
      acciones.style.marginTop = '12px';
      const btn = document.createElement('button');
      btn.id = 'btnSolicitarIntercambio';
      btn.className = 'btn-amarillo';
      btn.innerHTML = '<i class="fas fa-exchange-alt"></i> Solicitar intercambio';
      acciones.appendChild(btn);
      const modalContent = document.querySelector('#modal .modal-content .modal-text');
      if (modalContent) modalContent.appendChild(acciones);
    }

    // Asignar handler del botón
    const btnSolicitar = document.getElementById('btnSolicitarIntercambio');
    if (btnSolicitar) {
      btnSolicitar.onclick = async () => {
        try {
          const token = localStorage.getItem('jwtToken');
          const headers = { 'Content-Type': 'application/json' };
          if (token) headers['Authorization'] = 'Bearer ' + token;
          const r = await fetch('/api/intercambios/request', {
            method: 'POST',
            headers: headers,
            body: JSON.stringify({ libroId: libro.idLibro })
          });
          if (r.status === 201) {
            alert('Solicitud de intercambio enviada al propietario');
            if (modal) modal.style.display = 'none';
          } else if (r.status === 400) {
            const data = await r.json();
            alert(data.error || 'No se pudo enviar la solicitud');
          } else if (r.status === 401) {
            alert('Inicia sesión para solicitar intercambios');
            window.location.href = '/login.html';
          } else {
            const data = await r.json();
            alert(data.error || 'Error al solicitar intercambio');
          }
        } catch (err) {
          console.error('Error solicitando intercambio', err);
          alert('Error solicitando intercambio');
        }
      };
    }
    
    // Mostrar modal
    if (modal) modal.style.display = 'block';

  } catch (e) {
    console.error('Error:', e);

    Swal.fire({
      icon: "error",
      title: "Error",
      text: "No se pudo cargar el libro."
    });
  }
}

function editarLibro(id) {
  window.location.href = `/libros/editar.html?id=${id}`;
}

document.addEventListener('DOMContentLoaded', cargarInventario);
