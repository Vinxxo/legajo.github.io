<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Dashboard Usuario</title>
    <link rel="stylesheet" href="../estilos/dashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <button id="toggleModo" class="modo-toggle">🌙 Modo Claro</button>
    <!-- Sidebar -->
    <nav class="sidebar">
        <div class="logo">
            <i class="fas fa-user"></i>
            <span>Panel de usuario</span>
        </div>
        <ul class="nav-links">
            <a href=""><li class="active"><i class="fas fa-home"></i><span>Inicio</span></li></a>
            <a href="chats.html"><li><i class="fas fa-message"></i><span>Chats</span></li></a>
            <a href="inventario.html"><li><i class="fas fa-box"></i><span>Inventario</span></li></a>
            <a href=""><li><i class="fas fa-check"></i><span>Notificaciones</span></li></a>
            <a href="perfil.html"><li><i class="fas fa-user-circle"></i><span>Perfil</span></li></a>
        </ul>
    </nav>

    <!-- Contenido Principal -->
    <main class="main-content">
        <header>
            <div class="header-left">
                <h1>Bienvenid@</h1>
                <p><span id="nombreRecibido">nombre</span>, Como estás?</p>
            </div>
            <div class="header-right">
                <div class="user-profile">
                    <img src="../imagenes/profile.png" alt="Usuario" />
                    <span>Usuario</span>
                </div>
            </div>
        </header>

       <section class="seccion-carrusel">
            <h2 class="titulo-seccion">Recomendados</h2>
            <div class="contenedor-carrusel">
                <button class="flecha izquierda" onclick="moverCarrusel('recomendados', -1)">❮</button>
                <div class="carrusel-libros" id="recomendados">
                <div class="libro"><img src="../imagenes/libro_de_la_selva.jpg"><h3>Hábitos Atómicos</h3><p>James Clear</p><p>⭐⭐⭐⭐⭐</p><button class="ver-libro"><i class="fa fa-eye"></i> Ver</button></div>
                <div class="libro"><img src="../imagenes/libro_de_la_selva.jpg"><h3>Pensar rápido</h3><p>Daniel Kahneman</p><p>⭐⭐⭐⭐</p><button class="ver-libro"><i class="fa fa-eye"></i> Ver</button></div>
                <div class="libro"><img src="../imagenes/libro_de_la_selva.jpg"><h3>El poder de los hábitos</h3><p>Charles Duhigg</p><p>⭐⭐⭐⭐⭐</p><button class="ver-libro"><i class="fa fa-eye"></i> Ver</button></div>
                <div class="libro"><img src="../imagenes/libro_de_la_selva.jpg"><h3>Padre Rico</h3><p>Robert Kiyosaki</p><p>⭐⭐⭐</p><button class="ver-libro"><i class="fa fa-eye"></i> Ver</button></div>
                </div>
                <button class="flecha derecha" onclick="moverCarrusel('recomendados', 1)">❯</button>
            </div>
        </section>

        <section class="seccion-carrusel">
            <h2 class="titulo-seccion">Autores más leídos</h2>
            <div class="contenedor-carrusel">
                <button class="flecha izquierda" onclick="moverCarrusel('autores', -1)">❮</button>
                <div class="carrusel-libros" id="autores">
                <div class="libro"><img src="../imagenes/gabo.jpg"><h3>James Clear</h3><p>Hábitos Atómicos</p><p>⭐⭐⭐⭐⭐</p><button class="ver-libro"><i class="fa fa-eye"></i> Ver</button></div>
                <div class="libro"><img src="../imagenes/gabo.jpg"><h3>Stephen King</h3><p>It</p><p>⭐⭐⭐⭐</p><button class="ver-libro"><i class="fa fa-eye"></i> Ver</button></div>
                <div class="libro"><img src="../imagenes/gabo.jpg"><h3>J.K. Rowling</h3><p>Harry Potter</p><p>⭐⭐⭐⭐⭐</p><button class="ver-libro"><i class="fa fa-eye"></i> Ver</button></div>
                <div class="libro"><img src="../imagenes/gabo.jpg"><h3>Gabriel García Márquez</h3><p>Cien años de soledad</p><p>⭐⭐⭐⭐⭐</p><button class="ver-libro"><i class="fa fa-eye"></i> Ver</button></div>
                <div class="libro"><img src="../imagenes/gabo.jpg"><h3>George Orwell</h3><p>1984</p><p>⭐⭐⭐⭐</p><button class="ver-libro"><i class="fa fa-eye"></i> Ver</button></div>
                <div class="libro"><img src="../imagenes/gabo.jpg"><h3>Haruki Murakami</h3><p>Tokio Blues</p><p>⭐⭐⭐</p><button class="ver-libro"><i class="fa fa-eye"></i> Ver</button></div>
                </div>
                <button class="flecha derecha" onclick="moverCarrusel('autores', 1)">❯</button>
            </div>
        </section>


        <section class="seccion-carrusel">
            <h2 class="titulo-seccion">Géneros</h2>
            <div class="contenedor-carrusel">
                <button class="flecha izquierda" onclick="moverCarrusel('generos', -1)">❮</button>
                <div class="carrusel-libros" id="generos">
                <div class="libro"><img src="../imagenes/terror.jpeg"><h3>Autoayuda</h3><p>Libros que motivan</p><p>⭐⭐⭐⭐</p><button class="ver-libro"><i class="fa fa-eye"></i> Ver</button></div>
                <div class="libro"><img src="../imagenes/terror.jpeg"><h3>Ficción</h3><p>Historias creativas</p><p>⭐⭐⭐⭐⭐</p><button class="ver-libro"><i class="fa fa-eye"></i> Ver</button></div>
                <div class="libro"><img src="../imagenes/terror.jpeg"><h3>Misterio</h3><p>Tramas intrigantes</p><p>⭐⭐⭐</p><button class="ver-libro"><i class="fa fa-eye"></i> Ver</button></div>
                <div class="libro"><img src="../imagenes/terror.jpeg"><h3>Fantasía</h3><p>Mundos mágicos</p><p>⭐⭐⭐⭐</p><button class="ver-libro"><i class="fa fa-eye"></i> Ver</button></div>
                <div class="libro"><img src="../imagenes/terror.jpeg"><h3>Biografías</h3><p>Vidas inspiradoras</p><p>⭐⭐⭐</p><button class="ver-libro"><i class="fa fa-eye"></i> Ver</button></div>
                <div class="libro"><img src="../imagenes/terror.jpeg"><h3>Romance</h3><p>Historias de amor</p><p>⭐⭐⭐⭐⭐</p><button class="ver-libro"><i class="fa fa-eye"></i> Ver</button></div>
                </div>
                <button class="flecha derecha" onclick="moverCarrusel('generos', 1)">❯</button>
            </div>
        </section>

    </main>
    <script src="../scripts/script.js"></script>
    <script>
        function moverCarrusel(id, direccion) {
        const carrusel = document.getElementById(id);
        const libros = carrusel.querySelectorAll('.libro');
        const anchoLibro = libros[0].offsetWidth + 16;

        carrusel.scrollLeft += direccion * anchoLibro;

        const scrollMax = carrusel.scrollWidth - carrusel.clientWidth;

        // Movimiento infinito fluido
        if (direccion === 1 && carrusel.scrollLeft >= scrollMax - 5) {
            carrusel.scrollLeft = 0;
        } else if (direccion === -1 && carrusel.scrollLeft <= 0) {
            carrusel.scrollLeft = scrollMax;
        }
        }
    </script>
    <script>
        const inicial = localStorage.getItem("textoCompartido");
        if (inicial){
            document.getElementById("nombreRecibido").textContent = inicial;
        }
        
        window.addEventListener("storage", function(event){
            if (event.key === "textoCompartido"){
                document.getElementById("nombreRecibido").textContent = event.newValue;
            }
        });
    </script>
</body>
</html>