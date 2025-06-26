/* ==================
    BOTON DE TEMA
================== */

const toggle = document.getElementById("toggleModo");
const body = document.body;

// Verificar si ya hay un modo guardado
if (localStorage.getItem("modo") === "claro") {
    body.classList.add("modo-claro");
    toggle.textContent = "🌙 Modo Oscuro";
}

toggle.addEventListener("click", () => {
    body.classList.toggle("modo-claro");
    const esClaro = body.classList.contains("modo-claro");
    toggle.textContent = esClaro ? "🌙 Modo Oscuro" : "☀️ Modo Claro";
    localStorage.setItem("modo", esClaro ? "claro" : "oscuro");
});

/* ========================
    GRAFICAS DEL ADMIN
======================== */

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
});