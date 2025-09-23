<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB; // Para usar Query Builder

class UsuarioController extends Controller
{
    /**
     * Mostrar el formulario de registro
     */
    public function showRegisterForm()
    {
        return view('auth.registrar_usuario');
    }

    /**
     * Guardar un nuevo usuario en la BD
     */
    public function store(Request $request)
    {
        // ✅ Validar datos del formulario
        $request->validate([
            'NomUsu1'   => 'required|string|max:50',
            'ApeUsu1'   => 'required|string|max:50',
            'CorreoUsu' => 'required|email|unique:usuarios,CorreoUsu',
            'Clave'     => 'required|string|min:6|confirmed', // usa Clave + Clave_confirmation
            'FK_roles'  => 'required|integer',
        ]);

        // ✅ Insertar en la base de datos (tabla usuarios)
        DB::table('usuarios')->insert([
            'NomUsu1'      => $request->NomUsu1,
            'NomUsu2'      => $request->NomUsu2,
            'ApeUsu1'      => $request->ApeUsu1,
            'ApeUsu2'      => $request->ApeUsu2,
            'CorreoUsu'    => $request->CorreoUsu,
            'Clave'        => bcrypt($request->Clave), // encriptar clave
            'DireccionUsu' => $request->DireccionUsu,
            'CiudadUsu'    => $request->CiudadUsu,
            'TelefonoUsu'  => $request->TelefonoUsu,
            'FK_roles'     => $request->FK_roles, // 👈 viene del select en tu formulario
        ]);

        // ✅ Redirigir con mensaje de éxito
        return redirect()->route('home')->with('success', 'Usuario registrado correctamente ✅');
    }
}
