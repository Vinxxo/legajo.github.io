// dashboard.js - Carga dinámica de libros para el dashboard

let usuarioActualId = null;
let todosLosLibros = [];

// Obtener el ID del usuario autenticado
async function obtenerUsuarioActual() {
    try {
        const token = localStorage.getItem('jwtToken');
        if (!token) return null;
        
        const resp = await fetch('/api/auth/me', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token,
                'Accept': 'application/json'
            }
        });
        
        if (!resp.ok) return null;
        const user = await resp.json();
        return user.idUsuario || user.id;
    } catch (e) {
        console.error('Error obteniendo usuario actual:', e);
        return null;
    }
}

// Cargar todos los libros de la API
async function cargarTodosLosLibros() {
    try {
        const resp = await fetch('/api/libros');
        if (!resp.ok) throw new Error('Error al cargar libros');
        const libros = await resp.json();
        console.debug('📚 LIBROS CARGADOS DEL API:', libros);
        if (libros && libros.length > 0) {
            console.debug('Primer libro estructura:', libros[0]);
        }
        return libros || [];
    } catch (e) {
        console.error('Error cargando libros:', e);
        return [];
    }
}

// Filtrar libros que NO sean del usuario actual
function filtrarLibrosOtrosUsuarios(libros, usuarioId) {
    if (!usuarioId) {
        console.debug('Sin usuarioId, mostrando todos los libros');
        return libros;
    }
    
    console.debug('=== FILTRADO DE LIBROS ===');
    console.debug('usuarioId actual:', usuarioId);
    console.debug('Total de libros:', libros.length);
    
    // Filtrar: solo mostrar libros que NO sean del usuario actual
    const resultado = libros.filter(libro => {
        // Intentar obtener el ID del propietario de diferentes formas
        const propietarioId = libro.usuarioPropietarioId || libro.propietarioId;
        const esDelUsuario = propietarioId === usuarioId;
        
        console.debug(`Libro: "${libro.titulo}", propietarioId:${propietarioId}, esDelUsuario:${esDelUsuario}`);
        
        return propietarioId !== usuarioId && propietarioId > 0; // Solo mostrar si es diferente Y tiene ID válido
    });
    
    console.debug('Libros que se mostrarán:', resultado.length);
    return resultado;
}

// Crear elementos HTML para los libros dinámicos
function crearElementoLibro(libro) {
    const div = document.createElement('div');
    div.className = 'libro';
    div.dataset.id = libro.idLibro || libro.id || '';
    div.dataset.titulo = libro.titulo || '';
    div.dataset.autor = libro.autor || '';
    div.dataset.descripcion = libro.descripcion || libro.sinopsis || 'Sin descripción disponible';
    div.dataset.imagen = libro.urlImagen || '/imgs/libro_de_la_selva.jpg';
    // propietario: nombre y id (si vienen)
    div.dataset.usuario = libro.usuario || (libro.usuarioPropietario ? libro.usuarioPropietario.nombre : 'Propietario desconocido');
    div.dataset.usuarioId = libro.usuarioPropietarioId || (libro.usuarioPropietario && (libro.usuarioPropietario.idUsuario || libro.usuarioPropietario.id)) || '';
    
    div.innerHTML = `
        <img src="${libro.urlImagen || '/imgs/libro_de_la_selva.jpg'}" alt="${libro.titulo || 'Libro'}">
        <h3>${libro.titulo || 'Sin título'}</h3>
        <p>${libro.autor || 'Autor desconocido'}</p>
        <div class="estrellas-display" data-libro-id="${libro.idLibro || libro.id}">⭐⭐⭐⭐⭐</div>
        <button class="ver-libro"><i class="fa fa-eye"></i> Ver</button>
    `;
    
    return div;
}

// Inicializar el dashboard con libros dinámicos
async function inicializarDashboard() {
    try {
        // Obtener usuario actual
        usuarioActualId = await obtenerUsuarioActual();
        
        // Cargar todos los libros
        todosLosLibros = await cargarTodosLosLibros();
        
        // Filtrar libros de otros usuarios
        const librosOtrosUsuarios = filtrarLibrosOtrosUsuarios(todosLosLibros, usuarioActualId);
        
        if (librosOtrosUsuarios.length === 0) {
            console.warn('No hay libros disponibles de otros usuarios');
            return;
        }
        
        // Limpiar carruseles
        const carruselRecomendados = document.getElementById('recomendados');
        const carruselGeneros = document.getElementById('generos');
        
        if (carruselRecomendados) {
            carruselRecomendados.innerHTML = '';
            // Agregar los primeros 6 libros al carrusel "Recomendados"
            librosOtrosUsuarios.slice(0, 6).forEach(libro => {
                carruselRecomendados.appendChild(crearElementoLibro(libro));
            });
        }
        
        if (carruselGeneros) {
            carruselGeneros.innerHTML = '';
            // Agregar los próximos 6 libros al carrusel "Géneros"
            librosOtrosUsuarios.slice(6, 12).forEach(libro => {
                carruselGeneros.appendChild(crearElementoLibro(libro));
            });
        }
        
        // Reattach event listeners para los nuevos botones
        attachVerLibroListeners();
        
        // Cargar calificaciones de todos los libros
        librosOtrosUsuarios.forEach(libro => {
            cargarPromedioLibroDashboard(libro.idLibro || libro.id);
        });
        
    } catch (e) {
        console.error('Error inicializando dashboard:', e);
    }
}

// Generar estrellas para mostrar calificación
function generarEstrellasDisplay(calificacion) {
    let html = '';
    for (let i = 1; i <= 5; i++) {
        if (i <= calificacion) {
            html += '<span style="color:#ffc107;">★</span>';
        } else {
            html += '<span style="color:#ddd;">☆</span>';
        }
    }
    return html + ` <span style="margin-left:5px; color:#666; font-size:0.9em;">${calificacion}/5</span>`;
}

// Cargar promedio de calificación en dashboard
async function cargarPromedioLibroDashboard(idLibro) {
    try {
        const token = localStorage.getItem('jwtToken');
        const headers = {};
        if (token) {
            headers['Authorization'] = 'Bearer ' + token;
        }
        
        const res = await fetch(`/api/calificaciones/libros/${idLibro}/promedio`, {
            headers: headers
        });
        if (res.ok) {
            const data = await res.json();
            const elemento = document.querySelector(`.estrellas-display[data-libro-id="${idLibro}"]`);
            if (elemento) {
                const promedio = data.promedio;
                const cantidad = data.cantidad;
                if (cantidad > 0) {
                    elemento.innerHTML = generarEstrellasDisplay(Math.round(promedio)) + 
                                        ` <span style="font-size:0.8em; color:#999;">(${cantidad})</span>`;
                } else {
                    elemento.innerHTML = '☆☆☆☆☆ <span style="font-size:0.8em; color:#999;">(sin calificaciones)</span>';
                }
            }
        }
    } catch (error) {
        console.error('Error cargando promedio:', error);
    }
}

// Cargar calificación actual y mostrar historial en modal
async function cargarCalificacionEnModal(idLibro) {
    try {
        const token = localStorage.getItem('jwtToken');
        const headers = {};
        if (token) {
            headers['Authorization'] = 'Bearer ' + token;
        }
        
        const res = await fetch(`/api/calificaciones/libros/${idLibro}/promedio`, {
            headers: headers
        });
        if (res.ok) {
            const data = await res.json();
            let calificacionEl = document.getElementById('modalCalificacion');
            if (!calificacionEl) {
                calificacionEl = document.createElement('div');
                calificacionEl.id = 'modalCalificacion';
                calificacionEl.style.marginTop = '12px';
                calificacionEl.style.padding = '10px';
                calificacionEl.style.backgroundColor = '#f5f5f5';
                calificacionEl.style.borderRadius = '5px';
                const modalText = document.querySelector('#modal .modal-content .modal-text');
                if (modalText) {
                    const h4Autor = modalText.querySelector('h4');
                    if (h4Autor) {
                        modalText.insertBefore(calificacionEl, h4Autor.nextSibling);
                    }
                }
            }
            
            if (data.cantidad > 0) {
                calificacionEl.innerHTML = `
                    <div style="margin-bottom:10px;">
                        <strong>Calificación promedio:</strong> ${generarEstrellasDisplay(Math.round(data.promedio))} (${data.cantidad} evaluaciones)
                    </div>
                    <button onclick="mostrarHistorialCalificaciones(${idLibro})" class="btn-azul" style="padding:8px 12px; font-size:0.9em;">
                        <i class="fas fa-history"></i> Ver Historial
                    </button>
                `;
            } else {
                calificacionEl.innerHTML = `<div><strong>Sin calificaciones aún</strong></div>`;
            }
        }
    } catch (error) {
        console.error('Error cargando calificación en modal:', error);
    }
}

// Attach listeners a los botones "Ver libro"
function attachVerLibroListeners() {
    const modal = document.getElementById('modal');
    if (!modal) return;
    console.debug('attachVerLibroListeners: start');
    
    const botonesVer = document.querySelectorAll('.ver-libro');
    botonesVer.forEach(btn => {
        // Remover listeners anteriores para evitar duplicados
        btn.onclick = null;
        
        btn.addEventListener('click', async (e) => {
            e.preventDefault();
            const libroDiv = btn.closest('.libro');
            if (libroDiv) {
                const titulo = libroDiv.dataset.titulo || '';
                const autor = libroDiv.dataset.autor || '';
                const descripcion = libroDiv.dataset.descripcion || '';
                const imagen = libroDiv.dataset.imagen || '/imgs/default-book.jpg';
                const usuario = libroDiv.dataset.usuario || 'Propietario desconocido';
                
                // Rellenar modal
                document.getElementById('modalImg').src = imagen;
                document.getElementById('modalTitulo').textContent = titulo;
                document.getElementById('modalAutor').textContent = autor;
                document.getElementById('modalDescripcion').textContent = descripcion;
                
                // Agregar información del propietario
                let propietarioEl = document.getElementById('modalPropietario');
                if (!propietarioEl) {
                    propietarioEl = document.createElement('h4');
                    propietarioEl.id = 'modalPropietario';
                    propietarioEl.style.color = '#666';
                    propietarioEl.style.fontSize = '0.95em';
                    propietarioEl.style.marginTop = '8px';
                    const modalText = document.querySelector('#modal .modal-content .modal-text');
                    if (modalText) {
                        const h4Autor = modalText.querySelector('h4');
                        if (h4Autor) {
                            modalText.insertBefore(propietarioEl, h4Autor.nextSibling);
                        } else {
                            modalText.insertBefore(propietarioEl, modalText.querySelector('h3'));
                        }
                    }
                }
                propietarioEl.textContent = `Propietario: ${usuario}`;

                // Cargar calificación del libro
                const libroId = libroDiv.dataset.id;
                await cargarCalificacionEnModal(libroId);
                
                // Añadir botón de solicitar intercambio en modal
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
                    const modalText = document.querySelector('#modal .modal-content .modal-text');
                    if (modalText) modalText.appendChild(acciones);
                    console.debug('attachVerLibroListeners: modalAcciones created');
                }

                // Asignar handler del botón (usa el id del libro en dataset)
                const btnSolicitar = document.getElementById('btnSolicitarIntercambio');
                if (btnSolicitar) {
                    // remove previous handler and set a fresh one that closes over libroDiv
                    btnSolicitar.onclick = null;
                    btnSolicitar.onclick = async () => {
                        try {
                            const libroId = (libroDiv && libroDiv.dataset && libroDiv.dataset.id) ? libroDiv.dataset.id : null;
                            console.debug('btnSolicitarIntercambio clicked, libroId=', libroId);
                            if (!libroId) {
                                alert('No se pudo identificar el libro a solicitar');
                                return;
                            }
                            const token = localStorage.getItem('jwtToken');
                            const headers = { 'Content-Type': 'application/json' };
                            if (token) headers['Authorization'] = 'Bearer ' + token;
                            const r = await fetch('/api/intercambios/request', {
                                method: 'POST',
                                headers: headers,
                                body: JSON.stringify({ libroId: libroId })
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
                modal.style.display = 'block';
            }
        });
    });
}

// Inicializar cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', inicializarDashboard);
