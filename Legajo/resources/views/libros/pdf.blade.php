<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <style>
    body { font-family: 'DejaVu Sans', sans-serif; font-size: 12px; color: #333; margin: 20px; }
    .header { text-align: center; margin-bottom: 20px; border-bottom: 2px solid #007bff; padding-bottom: 10px; }
    .header h3 { font-size: 18px; color: #007bff; margin: 0; }
    .filters { margin-bottom: 20px; font-size: 11px; background: #f8f9fa; padding: 10px; border-radius: 5px; }
    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
    th { background-color: #007bff; color: white; padding: 10px; font-size: 12px; text-align: left; border: 1px solid #ddd; }
    td { padding: 8px; font-size: 11px; border: 1px solid #ddd; }
    tbody tr:nth-child(even) { background-color: #f2f2f2; }
    tbody tr:hover { background-color: #e9ecef; }
    .footer { text-align: center; margin-top: 30px; font-size: 10px; color: #666; border-top: 1px solid #ddd; padding-top: 10px; }
  </style>
</head>
<body>
  <div class="header">
    <h3>Reporte de Libros</h3>
    <p>Generado el {{ date('d/m/Y') }}</p>
  </div>

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
          <td>{{ $libro->created_at ? $libro->created_at->format('d/m/Y') : 'N/A' }}</td>
        </tr>
      @endforeach
    </tbody>
  </table>

  <div class="footer">
    <p>Reporte generado automáticamente - Página 1</p>
  </div>
</body>
</html>
