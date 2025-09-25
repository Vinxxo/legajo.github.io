<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Editar Libro</title>
    <link rel="stylesheet" href="{{ asset('estilos/styles.css') }}">

</head>
<body>
    <div class="formulario">
        <form action="{{ route('libros.update', $libro->idLibro) }}" method="POST" enctype="multipart/form-data">
            @csrf
            @method('PUT')
            <h2><i class="fas fa-edit"></i> Editar Libro</h2>

            <input type="text" id="titulo" name="titulo" placeholder="Título del libro" value="{{ old('titulo', $libro->TituloLib) }}" required>

            <input type="text" id="autor" name="autor" placeholder="Autor" value="{{ old('autor', $libro->autores->pluck('NomAutor1')->first() . ' ' . $libro->autores->pluck('ApeAutor1')->first()) }}" required>

            <label for="sinopsis"></label>
            <textarea id="sinopsis" name="sinopsis" rows="4" placeholder="Escribe aquí la sinopsis..." required>{{ old('sinopsis', $libro->SinopsisLib) }}</textarea>

            <select id="genero" name="genero" required>
                <option value="">Seleccione un género</option>
                @if(isset($generos))
                    @foreach($generos as $genero)
                        <option value="{{ $genero->idGenero }}" {{ old('genero', $libro->generos->pluck('idGenero')->first()) == $genero->idGenero ? 'selected' : '' }}>{{ $genero->GeneroLib }}</option>
                    @endforeach
                @endif
            </select>

            <select id="estado" name="estado" required>
                <option value="">Seleccione el estado</option>
                <option value="Publicado" {{ old('estado', $libro->EstadoLib) == 'Publicado' ? 'selected' : '' }}>Publicado</option>
                <option value="Leyendo" {{ old('estado', $libro->EstadoLib) == 'Leyendo' ? 'selected' : '' }}>Leyendo</option>
            </select>

            <div class="form-grupo">
                <label for="imagen" class="label-file">Seleccionar nueva portada (opcional)</label>
                <input type="file" id="imagen" name="imagen" accept="image/*">
                @if($libro->Imagen)
                    <p>Imagen actual: <img src="{{ asset('storage/' . $libro->Imagen) }}" alt="Imagen actual" style="max-width: 100px;"></p>
                @endif
            </div>

            <button type="submit"><i class="fas fa-save"></i> Actualizar Libro</button>
        </form>
    </div>
</body>
</html>
