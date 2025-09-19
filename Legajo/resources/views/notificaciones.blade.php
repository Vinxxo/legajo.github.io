<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Dashboard Usuario</title>
    <link rel="stylesheet" href="../estilos/dashboard.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <button id="toggleModo" class="modo-toggle">🌙 Modo Claro</button>
    <!-- Sidebar -->
    <nav class="sidebar">
        <div class="logo">
            <i class="fas fa-user"></i>
            <span>Panel de <p id="n2">nombre</p></span>
        </div>
        <ul class="nav-links">
            <a href=""><li><i class="fas fa-home"></i><span>Inicio</span></li></a>
            <a href="chats.html"><li><i class="fas fa-message"></i><span>Chats</span></li></a>
            <a href="inventario.html"><li><i class="fas fa-box"></i><span>Inventario</span></li></a>
            <a href="notificaciones.html"><li class="active"><i class="fas fa-check"></i><span>Notificaciones</span></li></a>
            <a href="perfil.html"><li><i class="fas fa-user-circle"></i><span>Perfil</span></li></a>
        </ul>
    </nav>

    <!-- Contenido Principal -->
    <main class="main-content">
        <header>
            <div class="header-left">
                <h1>Notificaciones</h1>
                
            </div>
            <div class="header-right">
                <div class="user-profile">
                <img src="../imagenes/profile.png" alt="Usuario" />
                <span id="n3">Usuario</span>
                </div>
            </div>
        </header>
        <!-- Notificaciones  -->
        <section class="chat-lista">
            <div class="chat-item no-leido">
                <div class="chat-name">Juan Pérez</div>
                <div class="chat-info"></div>
                <div class="chat-last-message"> Hola, como vas con el libro?</div>
                
            </div>
                <div class="chat-item no-leido">
                <div class="chat-name">Claudia Gomez</div>
                <div class="chat-info"></div>
                <div class="chat-last-message">Solicitud de intercambio</div>
            </div>
                <div class="chat-item no-leido">
                <div class="chat-name">Maria Gonzales S</div>
                <div class="chat-info"></div>
                <div class="chat-last-message"> Mañana me llega el libro de Cro..</div>
                
            </div>
                <div class="chat-item">
                <div class="chat-name">Julian Lopez</div>
                <div class="chat-info"></div>
                <div class="chat-last-message">Solicitud de intercambio </div>
                
            </div>
                <div class="chat-item">
                <div class="chat-name">Julieth Aguirre</div>
                <div class="chat-info"></div>
                <div class="chat-last-message"> Solicitud de intercambio </div>
                
            </div>
                <div class="chat-item no-leido">
                <div class="chat-name">Maicol Sanchez Sanchez</div>
                <div class="chat-info"></div>
                <div class="chat-last-message"> Solicitud de intercambio</div>
                
            </div>
            

        </section>
    


    </main>
    <script>
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
    </script>
    
</body>
</html>