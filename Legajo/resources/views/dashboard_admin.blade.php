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
            <a href="dashboard_admin.html"><li class="active"><i class="fas fa-home"></i><span>Inicio</span></li></a>
            <a href="reportes_usuarios.html"><li><i class="fas fa-user"></i><span>Reporte de usuarios</span></li></a>
            <a href="../errores/500.html"><li><i class="fas fa-book"></i><span>Reporte de libros</span></li></a>
            <a href="perfil_admin.html"><li><i class="fas fa-user-circle"></i><span>Perfil</span></li></a>
            <a href="../errores/404.html"><li><i class="fas fa-cog"></i><span>Configuración</span></li></a>
        </ul>
    </nav>

    <!-- Contenido Principal -->
    <main class="main-content">
        <header>
            <div class="header-left">
                <h1>Hola David</h1>
                <p>Este es tu panel general de control</p>
            </div>
            <div class="header-right">
                <div class="user-profile">
                    <img src="../imagenes/profile.png" alt="Admin" />
                    <span>Administrador</span>
                </div>
            </div>
        </header>

        <section>
            <div class="summary-cards">
                <div class="card">
                    <div class="card-info">
                        <h3>Usuarios Registrados</h3>
                        <p class="number">1,234</p>
                        <p class="trend positive">+4.5% <span>último mes</span></p>
                    </div>
                </div>
                <div class="card">
                    <div class="card-info">
                        <h3>Registros</h3>
                        <p class="number">5,678</p>
                        <p class="trend negative">-1.2% <span>último mes</span></p>
                    </div>
                </div>
            </div>

            <div class="summary-cards">
                <div class="card card-canvas">
                    <div class="card-info">
                        <h3>Usuarios reportados este mes</h3>
                        <canvas id="uR" width="400" height="200"></canvas>
                    </div>
                </div>
                <div class="card card-canvas">
                    <div class="card-info">
                        <h3>Intercamibios completados este mes</h3>
                        <canvas id="iC" width="400" height="200"></canvas>
                    </div>
                </div>
            </div>
        </section>
    </main>
    <script src="../scripts/script.js"></script>
    
</html>