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

// Seleccionar todos los botones con clase 'ver-libro'
const openButtons = document.querySelectorAll('.ver-libro');

// Agregar event listener a cada botón (si existe modal)
if (modal) {
    openButtons.forEach(button => {
        button.onclick = () => modal.style.display = 'flex';
    });

    if (close) close.onclick = () => modal.style.display = 'none';

    window.onclick = (e) => {
        if (e.target === modal) modal.style.display = 'none';
    };
}
