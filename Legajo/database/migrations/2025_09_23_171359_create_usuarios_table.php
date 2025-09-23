<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration {
    public function up(): void {
        Schema::create('usuarios', function (Blueprint $table) {
            $table->id('idUsuario');
            $table->string('NomUsu1', 20);
            $table->string('NomUsu2', 20)->nullable();
            $table->string('ApeUsu1', 20);
            $table->string('ApeUsu2', 20)->nullable();
            $table->string('CorreoUsu', 50)->unique();
            $table->string('Clave', 8);
            $table->string('DireccionUsu', 50);
            $table->string('CiudadUsu', 15);
            $table->bigInteger('TelefonoUsu');
            $table->unsignedBigInteger('FK_roles');
        });
    }

    public function down(): void {
        Schema::dropIfExists('usuarios');
    }
};
