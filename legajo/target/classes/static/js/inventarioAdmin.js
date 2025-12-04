// /js/inventarioAdmin.js
// CRUD para inventario_admi.html (admin)
const API = '/api/libros';

document.addEventListener('DOMContentLoaded', cargarInventarioAdmin);

async function cargarInventarioAdmin() {
  const grid = document.querySelector('.grid-inventario-admin');
  if (!grid) return;
  grid.innerHTML = '';
  try {
    const res = await fetch(API);
    if (!res.ok) throw new Error('Error al cargar libros');
    const libros = await res.json();
    if (!libros.length) {
      grid.innerHTML = '<p>No hay libros en el inventario.</p>';
      return;
    }
    libros.forEach(libro => {
      const item = document.createElement('div');
      item.className = 'item-inventario-admin';
      item.innerHTML = `
        <img src="/imgs/libro_de_la_selva.jpg" />
        <h3>${libro.titulo || ''}</h3>
        <h4>${libro.autor || ''}</h4>
        <p>Dueño: <strong>${libro.usuario || ''}</strong></p>
        <p class="estado">Estado: ${libro.estado || ''}</p>
        <div class="estrellas">★★★★★</div>
        <div class="acciones-admin">
          <button class="btn-azul" onclick="verLibroAdmin('${libro.idLibro}')"><i class="fas fa-eye"></i></button>
          <button class="btn-amarillo" onclick="editarLibroAdmin('${libro.idLibro}')"><i class="fas fa-pen"></i></button>
          <button class="btn-rojo" onclick="eliminarLibroAdmin('${libro.idLibro}')"><i class="fas fa-trash"></i></button>
        </div>
      `;
      grid.appendChild(item);
    });
  } catch (e) {
    grid.innerHTML = '<p>Error cargando inventario.</p>';
  }
}

async function eliminarLibroAdmin(id) {
  if (!confirm('¿Seguro que deseas eliminar este libro?')) return;
  const res = await fetch(`${API}/${id}`, { method: 'DELETE' });
  if (res.ok) cargarInventarioAdmin();
  else alert('No se pudo eliminar');
}

function verLibroAdmin(id) {
  alert('Ver libro: ' + id);
}

function editarLibroAdmin(id) {
  alert('Editar libro: ' + id);
}
