<?php

namespace App\Models;

use Illuminate\Foundation\Auth\User as Authenticatable;

class Usuario extends Authenticatable
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

    protected $hidden = [
        'Clave',
    ];

    /**
     * Override the method to get the password for authentication.
     * Laravel expects the password field to be named 'password' by default,
     * so we need to tell it to use 'Clave' instead.
     */
    public function getAuthPassword()
    {
        return $this->Clave;
    }
}
