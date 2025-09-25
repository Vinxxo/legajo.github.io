@extends('layouts.app')

@section('content')
<div class="container">
    <h1>Detalles del Usuario</h1>
    <ul class="list-group">
        <li class="list-group-item"><strong>ID:</strong> {{ $usuario->idUsuario }}</li>
        <li class="list-group-item"><strong>Nombre:</strong> {{ $usuario->NomUsu1 }} {{ $usuario->ApeUsu1 }}</li>
        <li class="list-group-item"><strong>Correo:</strong> {{ $usuario->CorreoUsu }}</li>
        <li class="list-group-item"><strong>Ciudad:</strong> {{ $usuario->CiudadUsu }}</li>
    </ul>
    <a href="{{ route('usuarios.index') }}" class="btn btn-secondary mt-3">Volver</a>
</div>
@endsection
