<?php

namespace App\Models;

use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;

class Usuario extends Authenticatable
{
    use Notifiable;

    // Nombre de la tabla en tu base
    protected $table = 'usuarios';

    // Clave primaria
    protected $primaryKey = 'idUsuario';

    // Tu tabla no usa created_at ni updated_at
    public $timestamps = false;

    // Campos que se pueden llenar
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
        'FK_roles',
    ];

    // Campos ocultos al devolver datos
    protected $hidden = [
        'Clave',
    ];

    // Define explícitamente la columna de contraseña
    protected $authPasswordName = 'Clave';

    // Método para devolver la contraseña (opcional si usas $authPasswordName)
    public function getAuthPassword()
    {
        return $this->Clave;
    }

    // Define el identificador de autenticación
    public function getAuthIdentifierName()
    {
        return 'CorreoUsu';
    }
}