// Datos de prueba
const libros = [
    {
        titulo: "Cien años de soledad",
        autor: "Gabriel García Márquez",
        usuario: "carlos88",
        genero: "Ficción",
        calificacion: 5,
        estado: "Disponible",
        descripcion: "Una obra del realismo mágico...",
        imagen: "../static/imgs/gabo.jpg"
    },
    {
        titulo: "Duna",
        autor: "Frank Herbert",
        usuario: "ana22",
        genero: "Ciencia ficción",
        calificacion: 5,
        estado: "Intercambiado",
        descripcion: "Una de las mejores novelas del género...",
        imagen: "../static/imgs/terror.jpeg"
    }
];

// Mostrar tabla
function cargarTabla(lista) {
    const tbody = document.getElementById("tablaLibros");
    tbody.innerHTML = "";

    lista.forEach(lib => {
        const tr = document.createElement("tr");

        tr.innerHTML = `
            <td>${lib.titulo}</td>
            <td>${lib.autor}</td>
            <td>${lib.genero}</td>
            <td>${lib.usuario}</td>
            <td>${"⭐".repeat(lib.calificacion)}</td>
            <td>${lib.estado}</td>
            <td><button class="btn-ver">Ver</button></td>
        `;

        tr.querySelector(".btn-ver").onclick = () => abrirModal(lib);

        tbody.appendChild(tr);
    });
}

// Filtrar sin fechas
function filtrar() {
    let resultado = libros.filter(l => 
        (!filtroTitulo.value || l.titulo.toLowerCase().includes(filtroTitulo.value.toLowerCase())) &&
        (!filtroAutor.value || l.autor.toLowerCase().includes(filtroAutor.value.toLowerCase())) &&
        (!filtroUsuario.value || l.usuario.toLowerCase().includes(filtroUsuario.value.toLowerCase())) &&
        (!filtroGenero.value || l.genero === filtroGenero.value) &&
        (!filtroEstado.value || l.estado === filtroEstado.value) &&
        (!filtroCalificacion.value || l.calificacion >= parseInt(filtroCalificacion.value))
    );

    cargarTabla(resultado);
}

// Inputs
const filtroTitulo = document.getElementById("filtroTitulo");
const filtroAutor = document.getElementById("filtroAutor");
const filtroUsuario = document.getElementById("filtroUsuario");
const filtroGenero = document.getElementById("filtroGenero");
const filtroEstado = document.getElementById("filtroEstado");
const filtroCalificacion = document.getElementById("filtroCalificacion");

[filtroTitulo, filtroAutor, filtroUsuario].forEach(f => f.oninput = filtrar);
[filtroGenero, filtroEstado, filtroCalificacion].forEach(f => f.onchange = filtrar);

// Limpiar
document.getElementById("btnLimpiar").onclick = () => {
    filtroTitulo.value = "";
    filtroAutor.value = "";
    filtroUsuario.value = "";
    filtroGenero.value = "";
    filtroEstado.value = "";
    filtroCalificacion.value = "";

    cargarTabla(libros);
};

// Modal
function abrirModal(lib) {
    document.getElementById("modalTitulo").textContent = lib.titulo;
    document.getElementById("modalAutor").textContent = lib.autor;
    document.getElementById("modalGenero").textContent = lib.genero;
    document.getElementById("modalUsuario").textContent = lib.usuario;
    document.getElementById("modalCalificacion").textContent = "⭐".repeat(lib.calificacion);
    document.getElementById("modalEstado").textContent = lib.estado;
    document.getElementById("modalDescripcion").textContent = lib.descripcion;
    document.getElementById("modalImg").src = lib.imagen;

    document.getElementById("modalLibro").style.display = "flex";
}

document.getElementById("cerrarModal").onclick = () => {
    document.getElementById("modalLibro").style.display = "none";
};

window.onclick = (e) => {
    if (e.target.id === "modalLibro") {
        document.getElementById("modalLibro").style.display = "none";
    }
};

// Inicial
cargarTabla(libros);
