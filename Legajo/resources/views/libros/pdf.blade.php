<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <style>
    body { font-family: DejaVu Sans, sans-serif; font-size:12px; }
    h3 { text-align:center; margin-bottom:10px; }
    table { width:100%; border-collapse:collapse; }
    th, td { border:1px solid #000; padding:6px; font-size:11px; }
    .filters { margin-bottom:10px; font-size:11px; }
  </style>
</head>
<body>
  <h3>Reporte de Libros</h3>

  <div class="filters">
    <strong>Filtros aplicados:</strong>
    @if(!empty($filters['titulo'])) Título: {{ $filters['titulo'] }}; @endif
    @if(!empty($filters['autor'])) Autor: {{ $filters['autor'] }}; @endif
    @if(!empty($filters['categoria_id'])) Categoría: {{ $filters['categoria_id'] }}; @endif
    @if(!empty($filters['estado'])) Estado: {{ $filters['estado'] }}; @endif
  </div>

  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Título</th>
        <th>Autor</th>
        <th>Género</th>
        <th>Estado</th>
        <th>Registrado</th>
      </tr>
    </thead>
    <tbody>
      @foreach($libros as $libro)
        <tr>
          <td>{{ $libro->idLibro }}</td>
          <td>{{ $libro->TituloLib }}</td>
          <td>{{ $libro->autores->pluck('NomAutor1')->join(', ') }}</td>
          <td>{{ $libro->generos->pluck('GeneroLib')->join(', ') }}</td>
          <td>{{ $libro->EstadoLib }}</td>
          <td>{{ $libro->created_at ? $libro->created_at->format('Y-m-d') : 'N/A' }}</td>
        </tr>
      @endforeach
    </tbody>
  </table>
</body>
</html>
