// /js/notificaciones.js
// Muestra solicitudes de intercambio recibidas y permite ver inventario del solicitante y aceptar/rechazar
const API_INTER = '/api/intercambios';

async function cargarNotificaciones() {
  const cont = document.querySelector('.chat-lista');
  if (!cont) return;
  cont.innerHTML = '';
  try {
    const token = localStorage.getItem('jwtToken');
    const headers = { 'Accept': 'application/json' };
    if (token) headers['Authorization'] = 'Bearer ' + token;
    console.debug('cargarNotificaciones: fetching /api/intercambios/received with headers', headers);
    const res = await fetch(`${API_INTER}/received`, { headers });
    if (!res.ok) {
      cont.innerHTML = '<p>No se pudo obtener notificaciones.</p>';
      return;
    }
    const lista = await res.json();
    if (!lista.length) {
      cont.innerHTML = '<p>No hay solicitudes pendientes.</p>';
      return;
    }
    lista.forEach(i => {
      const div = document.createElement('div');
      div.className = 'chat-item no-leido';
      div.innerHTML = `
        <div class="chat-name">${i.nombreSolicitante}</div>
        <div class="chat-info">Solicitud por: <strong>${i.tituloSolicitado}</strong></div>
        <div class="chat-last-message">Fecha: ${i.fechaSolicitud ? new Date(i.fechaSolicitud).toLocaleString() : ''}</div>
        <div style="margin-top:8px">
          <button class="btn-amarillo" onclick="verInventarioSolicitante(${i.idIntercambio})">Ver inventario</button>
          <button class="btn-rojo" onclick="responderSolicitud(${i.idIntercambio}, 'reject')">Rechazar</button>
        </div>
      `;
      cont.appendChild(div);
    });
  } catch (e) {
    console.error(e);
    cont.innerHTML = '<p>Error cargando notificaciones.</p>';
  }
}

// Ver inventario del solicitante y permitir seleccionar libro para intercambio
async function verInventarioSolicitante(intercambioId) {
  try {
    const token = localStorage.getItem('jwtToken');
    const headers = { 'Accept': 'application/json' };
    if (token) headers['Authorization'] = 'Bearer ' + token;
    console.debug('verInventarioSolicitante: fetching requester-inventory for intercambioId=', intercambioId);
    
    const res = await fetch(`${API_INTER}/${intercambioId}/requester-inventory`, { headers });
    if (!res.ok) {
      const errData = await res.text();
      console.error('Error response:', res.status, errData);
      throw new Error(`Error ${res.status}: No se pudo obtener inventario`);
    }
    const libros = await res.json();
    console.debug('verInventarioSolicitante: received', libros.length, 'books');
    
    if (!libros || libros.length === 0) {
      alert('El solicitante no tiene libros en su inventario');
      return;
    }
    
    // Crear modal estructurado para listar libros con grid y estilos CSS
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.style.display = 'block';
    modal.style.zIndex = '10000';
    modal.innerHTML = `
      <div class="modal-content">
        <span class="close">&times;</span>
        <div class="modal-text">
          <h2>Inventario del solicitante</h2>
          <p class="muted">Selecciona un libro que desees recibir en el intercambio:</p>
          <div id="listaLibrosSeleccion" class="inventory-grid" aria-live="polite"></div>
        </div>
      </div>
    `;
    document.body.appendChild(modal);
    modal.querySelector('.close').onclick = () => { modal.remove(); };

    const listaDiv = modal.querySelector('#listaLibrosSeleccion');
    libros.forEach(l => {
      const item = document.createElement('div');
      item.className = 'inventory-item';

      const img = document.createElement('img');
      img.src = l.urlImagen || '/imgs/default-book.jpg';
      img.alt = l.titulo || 'Sin título';
      img.className = 'inventory-img';

      const meta = document.createElement('div');
      meta.className = 'inventory-meta';
      meta.innerHTML = `
        <strong class="inventory-title">${l.titulo || 'Sin título'}</strong>
        <div class="inventory-author">${l.autor || 'Autor desconocido'}</div>
      `;

      const actions = document.createElement('div');
      actions.className = 'inventory-actions';
      const btn = document.createElement('button');
      btn.className = 'btn-verde btn-small';
      btn.textContent = 'Seleccionar';
      btn.addEventListener('click', (ev) => seleccionarLibroParaIntercambio(intercambioId, l.id, ev));
      actions.appendChild(btn);

      item.appendChild(img);
      item.appendChild(meta);
      item.appendChild(actions);
      listaDiv.appendChild(item);
    });
  } catch (e) {
    console.error('Error en verInventarioSolicitante:', e);
    alert('No se pudo obtener inventario del solicitante: ' + e.message);
  }
}

async function seleccionarLibroParaIntercambio(intercambioId, libroId, event) {
  event.stopPropagation();
  if (!confirm('¿Seleccionar este libro para ofrecer en el intercambio?')) return;
  try {
    const token = localStorage.getItem('jwtToken');
    const headers = { 'Content-Type': 'application/json' };
    if (token) headers['Authorization'] = 'Bearer ' + token;
    console.debug('seleccionarLibroParaIntercambio: sending accept for intercambioId=', intercambioId, 'libroId=', libroId);
    
    const res = await fetch(`${API_INTER}/${intercambioId}/respond`, {
      method: 'POST',
      headers: headers,
      body: JSON.stringify({ action: 'accept', libroCambioId: String(libroId) })
    });
    if (!res.ok) {
      const data = await res.json();
      console.error('Error response:', res.status, data);
      alert(data.error || 'Error al aceptar intercambio');
      return;
    }
    const data = await res.json();
    alert('✓ Intercambio aceptado. Se abrirá WhatsApp para coordinar.');
    console.debug('seleccionarLibroParaIntercambio: success, whatsapp=', data.whatsapp);
    if (data.whatsapp) {
      window.open(data.whatsapp, '_blank');
    }
    // Cerrar modal y recargar notificaciones
    document.querySelectorAll('.modal').forEach(m => m.remove());
    cargarNotificaciones();
  } catch (e) {
    console.error('Error en seleccionarLibroParaIntercambio:', e);
    alert('Error procesando la aceptación: ' + e.message);
  }
}

async function responderSolicitud(intercambioId, action) {
  if (action === 'reject' && !confirm('Rechazar la solicitud?')) return;
  try {
    const token = localStorage.getItem('jwtToken');
    const headers = { 'Content-Type': 'application/json' };
    if (token) headers['Authorization'] = 'Bearer ' + token;
    const res = await fetch(`${API_INTER}/${intercambioId}/respond`, {
      method: 'POST',
      headers: headers,
      body: JSON.stringify({ action })
    });
    if (!res.ok) {
      const data = await res.json();
      alert(data.error || 'Error al responder');
      return;
    }
    alert('Acción procesada');
    cargarNotificaciones();
  } catch (e) {
    console.error(e);
    alert('Error al responder solicitud');
  }
}

// Inicializar
document.addEventListener('DOMContentLoaded', cargarNotificaciones);
