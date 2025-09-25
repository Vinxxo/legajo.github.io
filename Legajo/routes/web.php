<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\ProfileController;
use App\Http\Controllers\UsuarioController;

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
        return view('dashboard_admin'); // usa resources/views/dashboard_admin.blade.php
    })->name('admin.dashboard');

    Route::get('/usuario/dashboard', function () {
        return view('dashboard_usuario'); // usa resources/views/dashboard_usuario.blade.php
    })->name('usuario.dashboard');

    Route::get('/usuario/chats', function () {
        return view('chats'); // usa resources/views/chats.blade.php
    })->name('usuario.chats');

    Route::get('/usuario/inventario', function () {
        return view('inventario'); // usa resources/views/inventario.blade.php
    })->name('usuario.inventario');

    Route::get('/usuario/notificaciones', function () {
        return view('notificaciones'); // usa resources/views/notificaciones.blade.php
    })->name('usuario.notificaciones');

    Route::get('/usuario/perfil', function () {
        return view('perfil'); // usa resources/views/perfil.blade.php
    })->name('usuario.perfil');
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

// Guardar usuario en la BD
Route::post('/registrar_usuario', [UsuarioController::class, 'store'])->name('registrar_usuario.store');