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
        return libros || [];
    } catch (e) {
        console.error('Error cargando libros:', e);
        return [];
    }
}

// Filtrar libros que NO sean del usuario actual
function filtrarLibrosOtrosUsuarios(libros, usuarioId) {
    if (!usuarioId) return libros; // Si no hay usuario autenticado, mostrar todos
    
    // Filtrar: solo mostrar libros que NO sean del usuario actual
    return libros.filter(libro => {
        // Intentar obtener el ID del propietario de diferentes formas
        const propietarioId = libro.usuarioPropietarioId || libro.propietarioId;
        return propietarioId !== usuarioId;
    });
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
        <p>⭐⭐⭐⭐⭐</p>
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
        
    } catch (e) {
        console.error('Error inicializando dashboard:', e);
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
        
        btn.addEventListener('click', (e) => {
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
