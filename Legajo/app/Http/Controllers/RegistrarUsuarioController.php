<?php

namespace App\Http\Controllers;

use App\Models\Usuario;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

class RegistrarUsuarioController extends Controller
{
    public function store(Request $request)
    {
        // Validación
        $request->validate([
            'NomUsu1'   => 'required|max:20',
            'ApeUsu1'   => 'required|max:20',
            'CorreoUsu' => 'required|email|unique:usuarios,CorreoUsu',
            'Clave'     => 'required|min:8|confirmed', // usa el campo Clave_confirmation
            'FK_roles'  => 'required|in:1,2',
        ]);

        // Crear usuario
        $usuario = Usuario::create([
            'NomUsu1'     => $request->NomUsu1,
            'NomUsu2'     => $request->NomUsu2,
            'ApeUsu1'     => $request->ApeUsu1,
            'ApeUsu2'     => $request->ApeUsu2,
            'CorreoUsu'   => $request->CorreoUsu,
            'Clave'       => Hash::make($request->Clave), // encriptamos la clave
            'DireccionUsu'=> $request->DireccionUsu,
            'CiudadUsu'   => $request->CiudadUsu,
            'TelefonoUsu' => $request->TelefonoUsu,
            'FK_roles'    => $request->FK_roles,
        ]);

        // Redirigir con mensaje
        return redirect()->route('login')->with('success', 'Usuario registrado correctamente. Ahora puedes iniciar sesión.');
    }
}
