// /js/registrarLibro.js
// Maneja el registro de libros desde registrar_libro.html
const API = '/api/libros';

document.addEventListener('DOMContentLoaded', () => {
  const form = document.querySelector('form');
  if (!form) return;
  form.onsubmit = async (e) => {
    e.preventDefault();
    const titulo = document.getElementById('titulo').value.trim();
    const sinopsis = document.getElementById('sinopsis').value.trim();
    const estado = 'DISPONIBLE'; // O puedes obtenerlo de un select si lo agregas
    // Puedes agregar autor, genero, etc. si el backend lo soporta
    const libro = { titulo, sinopsis, estado };
    try {
      const res = await fetch(API, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(libro)
      });
      if (!res.ok) throw new Error('Error al registrar libro');
      alert('Libro registrado con éxito');
      form.reset();
      window.location.href = 'inventario.html';
    } catch (err) {
      alert('No se pudo registrar el libro');
    }
  };
});
