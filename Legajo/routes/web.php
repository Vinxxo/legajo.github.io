<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\ProfileController;
use App\Http\Controllers\UsuarioController;
use App\Http\Controllers\RegistrarUsuarioController;

// Página de inicio
Route::get('/', function () {
    return view('index');
})->name('home');

// Dashboard (solo para usuarios logueados)
Route::get('/dashboard', function () {
    return view('dashboard');
})->middleware(['auth', 'verified'])->name('dashboard');

// Perfil (solo para usuarios logueados)
Route::middleware('auth')->group(function () {
    Route::get('/profile', [ProfileController::class, 'edit'])->name('profile.edit');
    Route::patch('/profile', [ProfileController::class, 'update'])->name('profile.update');
    Route::delete('/profile', [ProfileController::class, 'destroy'])->name('profile.destroy');
});

// Aquí van las rutas de autenticación que trae Laravel Breeze/Fortify
require __DIR__.'/auth.php';

// --------------------------
// 🔹 Rutas personalizadas para usuarios
// --------------------------



// Mostrar formulario de registro (registrar_usuario.blade.php)
Route::get('/registrar_usuario', function () {
    return view('auth.registrar_usuario');
})->name('registrar_usuario');

//CRUD DE USUARIOS
Route::resource('usuarios', UsuarioController::class);


// Guardar usuario en la BD
Route::post('/registrar_usuario', [RegistrarUsuarioController::class, 'store'])->name('registrar_usuario.store');

