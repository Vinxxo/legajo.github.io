<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Usuario extends Model
{
    protected $table = 'usuarios';
    protected $primaryKey = 'idUsuario';
    public $timestamps = false;

    protected $fillable = [
        'NomUsu1',
        'NomUsu2',
        'ApeUsu1',
        'ApeUsu2',
        'CorreoUsu',
        'Clave',
        'DireccionUsu',
        'CiudadUsu',
        'TelefonoUsu',
        'FK_roles'
    ];
}
