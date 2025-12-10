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
    const res = await fetch(`${API_INTER}/${intercambioId}/requester-inventory`);
    if (!res.ok) throw new Error('No se pudo obtener inventario');
    const libros = await res.json();
    // Crear modal simple para listar libros
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.style.display = 'block';
    modal.innerHTML = `
      <div class="modal-content" style="max-width:700px">
        <span class="close">&times;</span>
        <h2>Inventario del solicitante</h2>
        <div id="listaLibrosSeleccion"></div>
      </div>
    `;
    document.body.appendChild(modal);
    modal.querySelector('.close').onclick = () => { modal.remove(); };
    const listaDiv = modal.querySelector('#listaLibrosSeleccion');
    libros.forEach(l => {
      const item = document.createElement('div');
      item.style.display = 'flex';
      item.style.alignItems = 'center';
      item.style.gap = '12px';
      item.style.margin = '8px 0';
      item.innerHTML = `
        <img src="${l.urlImagen || '/imgs/default-book.jpg'}" style="width:60px;height:80px;object-fit:cover">
        <div style="flex:1">
          <strong>${l.titulo}</strong>
        </div>
        <div>
          <button class="btn-verde" onclick="seleccionarLibroParaIntercambio(${intercambioId}, ${l.id}, this)">Seleccionar</button>
        </div>
      `;
      listaDiv.appendChild(item);
    });
  } catch (e) {
    console.error(e);
    alert('No se pudo obtener inventario del solicitante');
  }
}

async function seleccionarLibroParaIntercambio(intercambioId, libroId, btn) {
  if (!confirm('Seleccionar este libro para ofrecer en el intercambio?')) return;
  try {
    const token = localStorage.getItem('jwtToken');
    const headers = { 'Content-Type': 'application/json' };
    if (token) headers['Authorization'] = 'Bearer ' + token;
    const res = await fetch(`${API_INTER}/${intercambioId}/respond`, {
      method: 'POST',
      headers: headers,
      body: JSON.stringify({ action: 'accept', libroCambioId: String(libroId) })
    });
    if (!res.ok) {
      const data = await res.json();
      alert(data.error || 'Error al aceptar intercambio');
      return;
    }
    const data = await res.json();
    alert('Intercambio aceptado. Se abrirá WhatsApp para coordinar.');
    if (data.whatsapp) {
      window.open(data.whatsapp, '_blank');
    }
    // cerrar modal y recargar notificaciones
    document.querySelectorAll('.modal').forEach(m => m.remove());
    cargarNotificaciones();
  } catch (e) {
    console.error(e);
    alert('Error procesando la aceptación');
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
