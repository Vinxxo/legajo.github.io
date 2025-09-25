@extends('layouts.app')

@section('content')
<div class="container">
    <h1>Editar Usuario</h1>
    <form action="{{ route('usuarios.update', $usuario->idUsuario) }}" method="POST">
        @csrf @method('PUT')
        <div class="mb-3">
            <label>Primer Nombre</label>
            <input type="text" name="NomUsu1" class="form-control" value="{{ $usuario->NomUsu1 }}" required>
        </div>
        <div class="mb-3">
            <label>Primer Apellido</label>
            <input type="text" name="ApeUsu1" class="form-control" value="{{ $usuario->ApeUsu1 }}" required>
        </div>
        <div class="mb-3">
            <label>Correo</label>
            <input type="email" name="CorreoUsu" class="form-control" value="{{ $usuario->CorreoUsu }}" required>
        </div>
        <div class="mb-3">
            <label>Clave</label>
            <input type="password" name="Clave" class="form-control" value="{{ $usuario->Clave }}" required>
        </div>
        <button type="submit" class="btn btn-primary">Actualizar</button>
    </form>
</div>
@endsection
