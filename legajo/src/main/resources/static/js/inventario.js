// /js/inventario.js
// CRUD para inventario.html (usuario)
const API = '/api/libros';

async function cargarInventario() {
  const grid = document.querySelector('.grid-inventario');
  if (!grid) return;
  grid.innerHTML = '';
  try {
    const res = await fetch(API);
    if (!res.ok) throw new Error('Error al cargar libros');
    const libros = await res.json();
    if (!libros.length) {
      grid.innerHTML = '<p>No hay libros en tu inventario.</p>';
      return;
    }
    libros.forEach(libro => {
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
    grid.innerHTML = '<p>Error cargando inventario.</p>';
  }
}

async function eliminarLibro(id) {
  if (!confirm('¿Seguro que deseas eliminar este libro?')) return;
  const token = localStorage.getItem('jwtToken');
  const headers = {};
  if (token) {
    headers['Authorization'] = 'Bearer ' + token;
  }
  const res = await fetch(`${API}/${id}`, { 
    method: 'DELETE',
    headers: headers
  });
  if (res.ok) cargarInventario();
  else alert('No se pudo eliminar');
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
    
    // Mostrar modal
    if (modal) modal.style.display = 'block';
  } catch (e) {
    console.error('Error:', e);
    alert('No se pudo cargar el libro');
  }
}

function editarLibro(id) {
  window.location.href = `/libros/editar.html?id=${id}`;
}

document.addEventListener('DOMContentLoaded', cargarInventario);