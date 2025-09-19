<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Dashboard Administrador</title>
    <link rel="stylesheet" href="../estilos/dashboard.css"/>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">    
</head>
<body>
    <button id="toggleModo" class="modo-toggle">🌙 Modo Claro</button>
    <!-- Sidebar -->
    <nav class="sidebar">
        <div class="logo">
            <i class="fas fa-user-shield"></i>
            <span>AdminDash</spa>
        </div>
        <ul class="nav-links">
            <a href="dashboard_admin.html"><li><i class="fas fa-home"></i><span>Inicio</span></li></a>
            <a href="reportes_usuarios.html"><li class="active"><i class="fas fa-user"></i><span>Reporte de usuarios</span></li></a>
            <a href="../errores/500.html"><li><i class="fas fa-book"></i><span>Reporte de libros</span></li></a>
            <a href="perfil_admin.html"><li><i class="fas fa-user-circle"></i><span>Perfil</span></li></a>
            <a href="../errores/404.html"><li><i class="fas fa-cog"></i><span>Configuración</span></li></a>
        </ul>
    </nav>

    <!-- Contenido Principal -->
    <main class="main-content">
        <header>
            <div class="header-left">
                <h1 id="titulo-seccion">Reportes de usuarios</h1>
            </div>
            <div class="header-right">
                <div class="user-profile">
                <img src="../imagenes/profile.png" alt="Admin" />
                <span>Administrador</span>
                </div>
            </div>
        </header>

    <!-- Secciones del Dashboard -->
    <section>
        <div id="reporte-usuarios" class="contenido-seccion">
            <div class="grid-reportes">
            <div class="reporte-card">
            <p><strong>Reporte al usuario:</strong> Mariana_Gomez</p>
            <p><strong>Este reporte es:</strong><br> Publicó un libro con contenido ofensivo en la descripción.</p>
            <div class="botones-validacion">
                <button class="btn-negativo">❌</button>
                <button class="btn-positivo">✅</button>
            </div>
            </div>

            <div class="reporte-card">
            <p><strong>Reporte al usuario:</strong> Juan_Libros</p>
            <p><strong>Este reporte es:</strong><br> No entregó el libro acordado en el intercambio.</p>
            <div class="botones-validacion">
                <button class="btn-negativo">❌</button>
                <button class="btn-positivo">✅</button>
            </div>
            </div>

            <div class="reporte-card">
            <p><strong>Reporte al usuario:</strong> Sofi_Lectora</p>
            <p><strong>Este reporte es:</strong><br> Envió mensajes ofensivos por el chat privado.</p>
            <div class="botones-validacion">
                <button class="btn-negativo">❌</button>
                <button class="btn-positivo">✅</button>
            </div>
            </div>

            <div class="reporte-card">
            <p><strong>Reporte al usuario:</strong> Diego_Intercambio</p>
            <p><strong>Este reporte es:</strong><br> Está usando cuentas falsas para manipular intercambios.</p>
            <div class="botones-validacion">
                <button class="btn-negativo">❌</button>
                <button class="btn-positivo">✅</button>
            </div>
            </div>

            <div class="reporte-card">
            <p><strong>Reporte al usuario:</strong> Ana_cc</p>
            <p><strong>Este reporte es:</strong><br> Publicó un libro que ya había sido reportado anteriormente.</p>
            <div class="botones-validacion">
                <button class="btn-negativo">❌</button>
                <button class="btn-positivo">✅</button>
            </div>
            </div>

            <div class="reporte-card">
            <p><strong>Reporte al usuario:</strong> Leo_Fan</p>
            <p><strong>Este reporte es:</strong><br> Hizo spam masivo en los comentarios de otros usuarios.</p>
            <div class="botones-validacion">
                <button class="btn-negativo">❌</button>
                <button class="btn-positivo">✅</button>
            </div>
            </div>

        </div>
    </section>

    </main>
    <script src="../scripts/script.js"></script>
</body>
</html>