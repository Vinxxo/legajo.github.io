<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\ProfileController;
use App\Http\Controllers\UsuarioController;
use App\Http\Controllers\RegistrarUsuarioController;
use App\Http\Controllers\LibroController;

// Página de inicio
Route::get('/', function () {
    return view('index');
})->name('home');

// Dashboard (redirige según rol, solo para usuarios logueados)
Route::get('/dashboard', function () {
    $user = auth()->user();
    if ($user && (int) $user->FK_roles === 1) {
        return redirect()->route('admin.dashboard');
    }
    if ($user && (int) $user->FK_roles === 2) {
        return redirect()->route('usuario.dashboard');
    }
    return redirect()->route('home'); // sin vista genérica
})->middleware(['auth', 'verified', 'prevent-back-history'])->name('dashboard');

// Dashboards por rol (solo para usuarios logueados)
Route::middleware(['auth', 'verified', 'prevent-back-history'])->group(function () {
    Route::get('/admin/dashboard', function () {
        $user = auth()->user();
        return view('dashboard_admin', compact('user')); // usa resources/views/dashboard_admin.blade.php
    })->name('admin.dashboard');

    Route::get('/usuario/dashboard', function () {
        $user = auth()->user();
        return view('dashboard_usuario', compact('user')); // usa resources/views/dashboard_usuario.blade.php
    })->name('usuario.dashboard');

    Route::get('/usuario/chats', function () {
        return view('chats'); // usa resources/views/chats.blade.php
    })->name('usuario.chats');

    Route::get('/usuario/inventario', function () {
        $libros = \App\Models\Libro::with('autores', 'generos')->get();
        return view('inventario', compact('libros')); // usa resources/views/inventario.blade.php
    })->name('usuario.inventario');

    Route::get('/usuario/notificaciones', function () {
        return view('notificaciones'); // usa resources/views/notificaciones.blade.php
    })->name('usuario.notificaciones');

    Route::get('/usuario/perfil', function () {
        return view('perfil'); // usa resources/views/perfil.blade.php
    })->name('usuario.perfil');

    // Mostrar formulario de registro de libro (registrar_libro.blade.php)
    Route::get('/registrar_libro', function () {
        $generos = \App\Models\Genero::all();
        return view('auth.registrar_libro', compact('generos'));
    })->name('registrar_libro');
});

// Rutas para administradores (solo para usuarios logueados con rol de admin)
Route::middleware(['auth', 'verified', 'prevent-back-history'])->group(function () {
    Route::get('/admin/reportes-usuarios', function () {
        return view('reportes_usuarios'); // usa resources/views/reportes_usuarios.blade.php
    })->name('admin.reportes-usuarios');

    Route::get('/admin/perfil', function () {
        return view('perfil_admin'); // usa resources/views/perfil_admin.blade.php
    })->name('admin.perfil');
});

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

Route::get('libros/imprimir', [LibroController::class, 'imprimir'])->name('libros.imprimir');
Route::resource('libros', LibroController::class);
