/* ==================
    BOTON DE TEMA
================== */

const toggle = document.getElementById("toggleModo");
const body = document.body;

// Verificar si ya hay un modo guardado
if (localStorage.getItem("modo") === "claro") {
    body.classList.add("modo-claro");
    if (toggle) toggle.textContent = "🌙 Modo Oscuro";
}

if (toggle) {
    toggle.addEventListener("click", () => {
        body.classList.toggle("modo-claro");
        const esClaro = body.classList.contains("modo-claro");
        toggle.textContent = esClaro ? "🌙 Modo Oscuro" : "☀️ Modo Claro";
        localStorage.setItem("modo", esClaro ? "claro" : "oscuro");
    });
}

/* ========================
    GRAFICAS DEL ADMIN
======================== */

if (document.getElementById('uR')) {
    const uR = document.getElementById('uR').getContext('2d');

    new Chart(uR, {
        type: 'bar',
        data: {
            labels: [1,2,11,20,24,30],
            datasets: [{
                label: 'Cantidad de reportados (acumulados)',
                data: [5, 10, 20, 30, 40, 50],
                backgroundColor: '#999',
                borderRadius: 6
            }]
        },
        options: {
                plugins: { legend: { display: false } },
                scales: {
                y: { beginAtZero: true },
                x: { grid: { display: false } }
                }
            }
    });
}

if (document.getElementById('iC')) {
    const iC = document.getElementById('iC').getContext('2d');

    new Chart(iC, {
        type: 'line',
        data: {
            labels: [1,2,11,20,24,30],
            datasets: [{
                label: 'intercambios confirmados',
                data: [5, 25, 20, 30, 80, 100],
                backgroundColor: '#999',
                borderRadius: 6
            }]
        },
        options: {
                plugins: { legend: { display: false } },
                scales: {
                y: { beginAtZero: true },
                x: { grid: { display: false } }
                }
            }
        
    })
}

/* ======================
    VENTANA EMERGENTE
====================== */

const modal = document.getElementById('modal');
const close = document.getElementById('closeModal');

if (modal && close) {
    document.querySelectorAll(".ver-libro").forEach(boton => {
        boton.addEventListener("click", () => {
            const libro = boton.parentElement;

            const titulo = libro.dataset.titulo;
            const autor = libro.dataset.autor;
            const descripcion = libro.dataset.descripcion;
            const imagen = libro.dataset.imagen;

            document.getElementById("modalTitulo").textContent = titulo;
            document.getElementById("modalAutor").textContent = autor;
            document.getElementById("modalDescripcion").textContent = descripcion;
            document.getElementById("modalImg").src = imagen;

            modal.style.display = "flex";
        });
    });

    close.onclick = () => modal.style.display = 'none';
}

const modalAutor = document.getElementById("modalAutor");
const cerrarAutor = document.getElementById("cerrarAutor");

if (modalAutor && cerrarAutor) {
    document.querySelectorAll(".ver-autor").forEach(boton => {
        boton.addEventListener("click", () => {
            const card = boton.closest(".autor");

            document.getElementById("autorNombre").textContent = card.dataset.nombre;
            document.getElementById("autorDescripcion").textContent = card.dataset.descripcion;
            document.getElementById("autorLibros").textContent = card.dataset.libros;
            document.getElementById("autorImg").src = card.dataset.imagen;

            modalAutor.style.display = "block";
        });
    });

    cerrarAutor.onclick = () => modalAutor.style.display = "none";
}

window.onclick = (e) => {
    if (modal && e.target === modal) {
        modal.style.display = 'none';
    }
    if (modalAutor && e.target === modalAutor) {
        modalAutor.style.display = "none";
    }
};
