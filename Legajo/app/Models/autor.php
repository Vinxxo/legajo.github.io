<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Autor extends Model
{
    protected $table = 'autor';
    protected $primaryKey = 'idAutor';

    protected $fillable = [
        'NomAutor1',
        'ApeAutor1',
    ];

    public function libros()
    {
        return $this->belongsToMany(Libro::class, 'autor_libros', 'FK_idAutor', 'FK_idLibro');
    }
}
