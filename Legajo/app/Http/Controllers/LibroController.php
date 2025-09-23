<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;

class LibroController extends Controller
{
    /**
     * Mostrar el formulario de registrar libro
     */
    public function create()
    {
        // Carga la vista del formulario
        return view('auth.registrar_libro');
    }

    // ruta del index de los libros
    public function index()
{
    // Traer todos los libros con sus relaciones
    $libros = \DB::table('libros')
        ->leftJoin('autor_libros', 'libros.idLibro', '=', 'autor_libros.FK_idLibro')
        ->leftJoin('autor', 'autor.idAutor', '=', 'autor_libros.FK_idAutor')
        ->leftJoin('genero_libros', 'libros.idLibro', '=', 'genero_libros.FK_idLibro')
        ->leftJoin('genero', 'genero.idGenero', '=', 'genero_libros.FK_idGenero')
        ->select(
            'libros.*',
            'autor.NomAutor1',
            'autor.ApeAutor1',
            'genero.GeneroLib'
        )
        ->get();

    // Pasamos los libros a la vista
    return view('libros.index', compact('libros'));
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
        'autor'    => 'required|exists:autor,idAutor',
        'imagen'   => 'nullable|image|mimes:jpg,jpeg,png|max:2048',
    ]);

    // Guardar imagen si se subió
    $rutaImagen = null;
if ($request->hasFile('imagen')) {   
    $rutaImagen = $request->file('imagen')->store('libros', 'public');
}

// Guardamos libro
$idLibro = \DB::table('libros')->insertGetId([
    'TituloLib'   => $request->titulo,
    'SinopsisLib' => $request->sinopsis,
    'EstadoLib'   => $request->estado,
    'FK_usuarios' => auth()->id() ?? 1,
    'Imagen'      => $rutaImagen,  
]);

    // Relacionar autor
    \DB::table('autor_libros')->insert([
        'FK_idAutor' => $request->autor,
        'FK_idLibro' => $idLibro,
    ]);

    // Relacionar género
    \DB::table('genero_libros')->insert([
        'FK_idGenero' => $request->genero,
        'FK_idLibro'  => $idLibro,
    ]);

    return redirect()->route('libros.index')->with('success', 'Libro registrado con éxito');
}

}
