<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use App\Models\Usuario; // Reemplaza User por Usuario
use Illuminate\Auth\Events\Registered;
use Illuminate\Http\RedirectResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Validation\Rules;
use Illuminate\View\View;

class RegisteredUserController extends Controller
{
    /**
     * Display the registration view.
     */
    public function create(): View
    {
        return view('auth.register');
    }

    /**
     * Handle an incoming registration request.
     *
     * @throws \Illuminate\Validation\ValidationException
     */
    public function store(Request $request): RedirectResponse
{
    //dd($request->all()); // Primer dd que ya ejecutaste
    $request->validate([
        'NomUsu1' => ['required', 'string', 'max:20'],
        'NomUsu2' => ['nullable', 'string', 'max:20'],
        'ApeUsu1' => ['required', 'string', 'max:20'],
        'ApeUsu2' => ['nullable', 'string', 'max:20'],
        'CorreoUsu' => ['required', 'string', 'email', 'max:50', 'unique:usuarios'],
        'Clave' => ['required', 'string', 'min:8', 'confirmed'],
        'DireccionUsu' => ['nullable', 'string', 'max:50'],
        'CiudadUsu' => ['nullable', 'string', 'max:15'],
        'TelefonoUsu' => ['nullable', 'numeric', 'digits:10'],
        'FK_roles' => ['required', 'exists:roles,idRol'],
    ]);

    $usuario = Usuario::create([
        'NomUsu1' => $request->NomUsu1,
        'NomUsu2' => $request->NomUsu2,
        'ApeUsu1' => $request->ApeUsu1,
        'ApeUsu2' => $request->ApeUsu2,
        'CorreoUsu' => $request->CorreoUsu,
        'Clave' => Hash::make($request->Clave),
        'DireccionUsu' => $request->DireccionUsu,
        'CiudadUsu' => $request->CiudadUsu,
        'TelefonoUsu' => $request->TelefonoUsu,
        'FK_roles' => $request->FK_roles,
    ]);

    //dd($usuario); // Segundo dd para ver el usuario creado

    event(new Registered($usuario));
    Auth::login($usuario);
    return redirect(route('dashboard', absolute: false));
}
}
