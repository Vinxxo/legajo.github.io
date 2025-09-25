<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Genero extends Model
{
    protected $table = 'genero';
    protected $primaryKey = 'idGenero';

    protected $fillable = [
        'GeneroLib',
    ];

    public function libros()
    {
        return $this->belongsToMany(Libro::class, 'genero_libros', 'FK_idGenero', 'FK_idLibro');
    }
}
