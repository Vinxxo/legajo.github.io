<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use App\Models\Libro;
use App\Models\Genero;
use App\Models\Autor;
use Barryvdh\DomPDF\Facade\Pdf;
use Carbon\Carbon;

class LibroController extends Controller
{
    /**
     * Mostrar el formulario de registrar libro
     */
    public function create()
    {
        $generos = Genero::all();
        return view('auth.registrar_libro', compact('generos'));
    }

    /**
     * Mostrar la lista de libros con filtros
     */
    public function index(Request $request)
    {
        $query = Libro::with('autores', 'generos')->orderBy('created_at', 'desc');

        // Aplicar filtros
        $this->applyFilters($query, $request->all());

        $libros = $query->paginate(10);

        $categorias = Genero::all();

        // Pasamos los libros y categorías a la vista
        return view('libros.index', compact('libros', 'categorias'));
    }

    /**
     * Guardar libro en la base de datos
     */
    public function store(Request $request)
    {
        $request->validate([
            'titulo'   => 'required|max:100',
            'sinopsis' => 'required|max:400',
            'estado'   => 'required|in:Publicado,Leyendo',
            'genero'   => 'required|exists:genero,idGenero',
            'autor'    => 'required|string|max:100',
            'imagen'   => 'nullable|image|mimes:jpg,jpeg,png|max:2048',
        ]);

        // Procesar autor: dividir en nombre y apellido
        $autorParts = explode(' ', $request->autor, 2);
        $nomAutor1 = $autorParts[0] ?? '';
        $apeAutor1 = $autorParts[1] ?? '';

        // Buscar o crear autor
        $autor = Autor::firstOrCreate([
            'NomAutor1' => $nomAutor1,
            'ApeAutor1' => $apeAutor1,
        ]);

        // Guardar imagen si se subió
        $rutaImagen = null;
        if ($request->hasFile('imagen')) {
            $rutaImagen = $request->file('imagen')->store('libros', 'public');
        }

        // Guardar libro
        $idLibro = DB::table('libros')->insertGetId([
            'TituloLib'   => $request->titulo,
            'SinopsisLib' => $request->sinopsis,
            'EstadoLib'   => $request->estado,
            'FK_usuarios' => auth()->id() ?? 1,
            'imagen'      => $rutaImagen,
        ]);

        // Relacionar autor
        DB::table('autor_libros')->insert([
            'FK_idAutor' => $autor->idAutor,
            'FK_idLibro' => $idLibro,
        ]);

        // Relacionar género
        DB::table('genero_libros')->insert([
            'FK_idGenero' => $request->genero,
            'FK_idLibro'  => $idLibro,
        ]);

        return redirect()->route('libros.index')->with('success', 'Libro registrado con éxito');
    }

    /**
     * Generar PDF con filtros aplicados
     */
    public function imprimir(Request $request)
    {
        $filters = $request->only(['titulo', 'autor', 'categoria_id', 'estado', 'disponibilidad', 'date_from', 'date_to']);

        $query = Libro::with('autores', 'generos');
        $query = $this->applyFilters($query, $filters);

        $libros = $query->orderBy('created_at', 'desc')->get();

        $pdf = Pdf::loadView('libros.pdf', compact('libros', 'filters'))
                  ->setPaper('a4', 'landscape')
                  ->setOptions(['isRemoteEnabled' => true]);

        return $pdf->stream('reporte-libros.pdf');
    }

    /**
     * Aplicar filtros multicriterio a la consulta
     */
    protected function applyFilters($query, array $filters)
    {
        if (!empty($filters['titulo'])) {
            $query->where('TituloLib', 'like', '%' . $filters['titulo'] . '%');
        }

        if (!empty($filters['autor'])) {
            $query->whereHas('autores', function ($q) use ($filters) {
                $q->where('NomAutor1', 'like', '%' . $filters['autor'] . '%')
                  ->orWhere('ApeAutor1', 'like', '%' . $filters['autor'] . '%');
            });
        }

        if (!empty($filters['categoria_id'])) {
            $query->whereHas('generos', function ($q) use ($filters) {
                $q->where('idGenero', $filters['categoria_id']);
            });
        }

        if (!empty($filters['estado'])) {
            $query->where('EstadoLib', $filters['estado']);
        }

        // Disponibilidad no implementada aún, se puede agregar columna si es necesario

        if (!empty($filters['date_from']) && !empty($filters['date_to'])) {
            $from = Carbon::parse($filters['date_from'])->startOfDay();
            $to   = Carbon::parse($filters['date_to'])->endOfDay();
            $query->whereBetween('created_at', [$from, $to]);
        }

        return $query;
    }
}
