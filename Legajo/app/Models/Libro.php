<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Libro extends Model
{
    protected $table = 'libros';
    protected $primaryKey = 'idLibro';

    protected $fillable = [
        'TituloLib',
        'SinopsisLib',
        'EstadoLib',
        'FK_usuarios',
        'imagen'
    ];

    public function autores()
    {
        return $this->belongsToMany(Autor::class, 'autor_libros', 'FK_idLibro', 'FK_idAutor');
    }

    public function generos()
    {
        return $this->belongsToMany(Genero::class, 'genero_libros', 'FK_idLibro', 'FK_idGenero');
    }

    public function usuario()
    {
        return $this->belongsTo(Usuario::class, 'FK_usuarios', 'idUsuario');
    }
}
