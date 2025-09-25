<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Lista de Libros</title>
    <link rel="stylesheet" href="{{ asset('estilos/styles.css') }}">
    <style>
        .date{
            border-radius: 10px;
            color: black;
        }
        .libros-grid {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            margin-top: 20px;
            justify-content: center;
        }
        .libro-card {
            background: var(--color-secundario);
            border: 1px solid rgba(229,254,197,0.15);
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            text-align: center;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            width: 280px;
        }
        .libro-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 20px rgba(0,0,0,0.25);
        }
        .libro-card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: 8px;
            margin-bottom: 15px;
            padding: 10px;
            box-sizing: border-box;
        }
        .libro-card h3 {
            font-size: 1.2rem;
            margin-bottom: 10px;
            color: var(--color-texto);
        }
        .libro-card p {
            margin: 5px 0;
            font-size: 0.9rem;
            color: var(--color-texto);
        }
        .libro-card .estado {
            font-weight: bold;
            color: #ffb84d; /* same as button gradient */
        }
        .filtros-form {
            margin-bottom: 30px;
        }
        .filtros-form .grid-3cols {
            margin-bottom: 15px;
        }
        .pdf-link {
            display: inline-block;
            margin-bottom: 20px;
            padding: 10px 15px;
            background: linear-gradient(90deg, #ff7a59 0%, #ffb84d 100%);
            color: #1f1020;
            text-decoration: none;
            border-radius: 8px;
            transition: transform 0.2s;
        }
        .pdf-link:hover {
            transform: translateY(-2px);
        }
        .pagination-container {
            margin-top: 1rem;
            text-align: center;
        }

    </style>
</head>
<body>
    <div class="formulario">
        

        <form method="GET" action="{{ route('libros.index') }}" class="mb-6">
            <h2>Lista de Libros</h2>
            <div class="grid-3cols">
                <input type="text" name="titulo" value="{{ request('titulo') }}" placeholder="Título">
                <input type="text" name="autor" value="{{ request('autor') }}" placeholder="Autor">
                <select name="categoria_id">
                    <option value="">Todas las categorías</option>
                    @if(isset($categorias) && $categorias->count())
                        @foreach($categorias as $c)
                        <option value="{{ $c->idGenero }}" {{ request('categoria_id') == $c->idGenero ? 'selected' : '' }}>
                            {{ $c->GeneroLib }}
                        </option>
                        @endforeach
                    @else
                    <option disabled>No hay categorías disponibles</option>
                    @endif
                </select>
            </div>
            <div class="grid-1cols">
                <select name="estado">
                    <option value="">Todos los estados</option>
                    <option value="Publicado" {{ request('estado') == 'Publicado' ? 'selected' : '' }}>Publicado</option>
                    <option value="Leyendo" {{ request('estado') == 'Leyendo' ? 'selected' : '' }}>Leyendo</option>
                </select>
            </div>
            <button type="submit">Filtrar</button>
            <a href="{{ route('libros.imprimir', request()->query()) }}" target="_blank" class="pdf-link">Generar PDF</a>
        </form>

        

        <div class="libros-grid">
            @foreach($libros as $libro)
            <div class="libro-card">
                @if($libro->imagen)
                    <img src="{{ asset('storage/'.$libro->imagen) }}" alt="Imagen del libro">
                @else
                    <div style="height: 200px; background: var(--color-secundario); display: flex; align-items: center; justify-content: center; border-radius: 8px; margin-bottom: 15px;">No imagen</div>
                @endif
                <h3>{{ $libro->TituloLib }}</h3>
                <p><strong>Autor:</strong> {{ $libro->autores->pluck('NomAutor1')->join(', ') }}</p>
                <p><strong>Género:</strong> {{ $libro->generos->pluck('GeneroLib')->join(', ') }}</p>
                <p class="estado"><strong>Estado:</strong> {{ $libro->EstadoLib }}</p>
            </div>
            @endforeach
        </div>

        <div class="pagination-container">
            {{ $libros->links() }}
        </div>
    </div>
</body>
</html>
