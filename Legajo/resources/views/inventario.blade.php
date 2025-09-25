<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Dashboard Usuario</title>
    <link rel="stylesheet" href="../estilos/dashboard.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .acciones {
            display: flex;
            gap: 10px;
            margin-top: 10px;
        }
        .btn-editar {
            background: #3b82f6;
            color: white;
            padding: 8px 12px;
            border: none;
            border-radius: 6px;
            text-decoration: none;
            font-size: 0.9rem;
            transition: background 0.3s;
        }
        .btn-editar:hover {
            background: #2563eb;
        }
        .btn-borrar {
            background: #ef4444;
            color: white;
            padding: 8px 12px;
            border: none;
            border-radius: 6px;
            font-size: 0.9rem;
            cursor: pointer;
            transition: background 0.3s;
        }
        .btn-borrar:hover {
            background: #dc2626;
        }
    </style>
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
            <a href="{{ route('usuario.dashboard') }}"><li><i class="fas fa-home"></i><span>Inicio</span></li></a>
            <a href="{{ route('usuario.chats') }}"><li><i class="fas fa-message"></i><span>Chats</span></li></a>
            <a href="{{ route('usuario.inventario') }}"><li class="active"><i class="fas fa-box"></i><span>Inventario</span></li></a>
            <a href="{{ route('usuario.notificaciones') }}"><li><i class="fas fa-check"></i><span>Notificaciones</span></li></a>
            <a href="{{ route('usuario.perfil') }}"><li><i class="fas fa-user-circle"></i><span>Perfil</span></li></a>
        </ul>
    </nav>

    <!-- Contenido Principal -->
    <main class="main-content">
        <header>
            <div class="header-left">
                <h1>Tu inventario</h1>
                
            </div>
            <div class="header-right">
                <div class="user-profile">
                    <img src="../imagenes/profile.png" alt="Usuario" />
                    <span id="n3">Usuario</span>
                </div>
                <form method="POST" action="{{ route('logout') }}" style="margin-left: 12px;">
                    @csrf
                    <button type="submit" class="btn-logout" style="background:#ef4444;color:#fff;border:none;border-radius:6px;padding:8px 12px;cursor:pointer;">
                        Cerrar sesión
                    </button>
                </form>
            </div>
        </header>

        <!------
        INVENTARIO
        -------->

        <section>
            <div class="inventario">
                <div class="grid-inventario">
                    @foreach($libros as $libro)
                    <div class="item-inventario">
                        <img src="{{ asset('storage/' . $libro->Imagen) }}" alt="Imagen del libro">
                        <h3>{{ $libro->TituloLib }}</h3>
                        <h4>{{ $libro->autores->pluck('NomAutor1')->join(', ') }}</h4>
                        <div class="estrellas">
                            ★★★★☆
                        </div>
                        <p class="descripcion">{{ $libro->SinopsisLib }}</p>
                        <div class="acciones">
                            <button class="btn-verde"><i class="fas fa-eye"></i> Ver</button>
                            <a href="{{ route('libros.edit', $libro->idLibro) }}" class="btn-editar"><i class="fas fa-edit"></i> Editar</a>
                            <form method="POST" action="{{ route('libros.destroy', $libro->idLibro) }}" style="display:inline;">
                                @csrf
                                @method('DELETE')
                                <button type="submit" class="btn-borrar" onclick="return confirm('¿Estás seguro de que quieres borrar este libro?')"><i class="fas fa-trash"></i> Borrar</button>
                            </form>
                        </div>
                    </div>
                    @endforeach
                </div>

                <!-- Botón de acción (esquina inferior derecha) -->
                <a href="{{ route('registrar_libro') }}"><button class="btn-agregar">+</button></a>
            </div>
        </section>
    </main>
    <script src="../scripts/script.js"></script>
    
</body>
</html>