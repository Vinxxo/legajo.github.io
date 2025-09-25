<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Dashboard Usuario</title>
    <link rel="stylesheet" href="{{ asset('estilos/dashboard.css') }}">
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
            <a href="{{ route('usuario.dashboard') }}"><li><i class="fas fa-home"></i><span>Inicio</span></li></a>
            <a href="{{ route('usuario.chats') }}"><li class="active"><i class="fas fa-message"></i><span>Chats</span></li></a>
            <a href="{{ route('usuario.inventario') }}"><li><i class="fas fa-box"></i><span>Inventario</span></li></a>
            <a href="{{ route('usuario.notificaciones') }}"><li><i class="fas fa-check"></i><span>Notificaciones</span></li></a>
            <a href="{{ route('usuario.perfil') }}"><li><i class="fas fa-user-circle"></i><span>Perfil</span></li></a>
        </ul>
    </nav>

    <!-- Contenido Principal -->
    <main class="main-content">
        <header>
            <div class="header-left">
                <h1>Chats</h1>
            </div>
            <div class="header-right">
                <div class="user-profile">
                <img src="{{ asset('imagenes/profile.png') }}" alt="Usuario" />
                <span>{{ Auth::user()->NomUsu1 ?? 'Usuario' }}</span>
                </div>
                <form method="POST" action="{{ route('logout') }}" style="margin-left: 12px;">
                    @csrf
                    <button type="submit" class="btn-logout" style="background:#ef4444;color:#fff;border:none;border-radius:6px;padding:8px 12px;cursor:pointer;">
                        Cerrar sesión
                    </button>
                </form>
            </div>
        </header>

        <section class="chat-lista">
            <div class="chat-item no-leido">
                <div class="chat-avatar"><img src="{{ asset('imagenes/profile.png') }}" alt=""></div>
                <div class="chat-name">Juan Pérez</div>
                <div class="chat-info"></div>
                <div class="chat-last-message"> Hola, como vas con el libro?</div>
                <div class="chat-status online"></div>
            </div>
                <div class="chat-item no-leido">
                <div class="chat-avatar"><img src="{{ asset('imagenes/profile.png') }}" alt=""></div>
                <div class="chat-name">Claudia Gomez</div>
                <div class="chat-info"></div>
                <div class="chat-last-message"> No, hoy termino el libro de...</div>
            </div>
                <div class="chat-item no-leido">
                <div class="chat-avatar"><img src="{{ asset('imagenes/profile.png') }}" alt=""></div>
                <div class="chat-name">Maria Gonzales S</div>
                <div class="chat-info"></div>
                <div class="chat-last-message"> Mañana me llega el libro de Cro..</div>
                <div class="chat-status online"></div>
            </div>
                <div class="chat-item">
                <div class="chat-avatar"><img src="{{ asset('imagenes/profile.png') }}" alt=""></div>
                <div class="chat-name">Julian Lopez</div>
                <div class="chat-info"></div>
                <div class="chat-last-message"> Si te gusto el libro?..</div>
                <div class="chat-status online"></div>
            </div>
                <div class="chat-item">
                <div class="chat-avatar"><img src="{{ asset('imagenes/profile.png') }}" alt=""></div>
                <div class="chat-name">Julieth Aguirre</div>
                <div class="chat-info"></div>
                <div class="chat-last-message"> Ese libro esta muy...</div>
                <div class="chat-status online"></div>
            </div>
                <div class="chat-item no-leido">
                <div class="chat-avatar"><img src="{{ asset('imagenes/profile.png') }}" alt=""></div>
                <div class="chat-name">Maicol Sanchez Sanchez</div>
                <div class="chat-info"></div>
                <div class="chat-last-message"> Cuando termines el libro me cuentas</div>
                <div class="chat-status online"></div>
            </div>
                <div class="chat-item">
                <div class="chat-avatar"><img src="{{ asset('imagenes/profile.png') }}" alt=""></div>
                <div class="chat-name">Milena A</div>
                <div class="chat-info"></div>
                <div class="chat-last-message"> Mañana hay nueva edicion de...</div>
            </div>

        </section>
    </main>
    <script src="{{ asset('scripts/script.js') }}"></script>
</body>
</html>