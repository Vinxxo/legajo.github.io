@extends('layouts.app')

@section('content')
<div class="container">
    <h1>Usuarios</h1>
    <a href="{{ route('usuarios.create') }}" class="btn btn-primary mb-3">Nuevo Usuario</a>

    <table class="table table-bordered">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Correo</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            @foreach ($usuarios as $usuario)
            <tr>
                <td>{{ $usuario->idUsuario }}</td>
                <td>{{ $usuario->NomUsu1 }} {{ $usuario->ApeUsu1 }}</td>
                <td>{{ $usuario->CorreoUsu }}</td>
                <td>
                    <a href="{{ route('usuarios.show',$usuario->idUsuario) }}" class="btn btn-info btn-sm">Ver</a>
                    <a href="{{ route('usuarios.edit',$usuario->idUsuario) }}" class="btn btn-warning btn-sm">Editar</a>
                    <form action="{{ route('usuarios.destroy',$usuario->idUsuario) }}" method="POST" style="display:inline">
                        @csrf @method('DELETE')
                        <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                    </form>
                </td>
            </tr>
            @endforeach
        </tbody>
    </table>
</div>
@endsection
