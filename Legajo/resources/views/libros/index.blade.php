

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Listado de Libros</title>
    <link rel="stylesheet" href="{{ asset('estilos/styles.css') }}">
</head>
<body>
    <h1>Listado de Libros</h1>

    @if(session('success'))
        <p style="color: green;">{{ session('success') }}</p>
    @endif

    <ul>
        @foreach ($libros as $libro)
            <li>
                <h3>{{ $libro->TituloLib }}</h3>
                <p>{{ $libro->SinopsisLib }}</p>
                <p><b>Estado:</b> {{ $libro->EstadoLib }}</p>
                @if($libro->Imagen)
                <img src="{{ asset('storage/'.$libro->Imagen) }}" width="120">
                @else
                <p>[Sin portada]</p>
                @endif
            </li>
        @endforeach
    </ul>
</body>
</html>

