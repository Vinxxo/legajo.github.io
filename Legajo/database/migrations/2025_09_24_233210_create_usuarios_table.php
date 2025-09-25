<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('usuarios', function (Blueprint $table) {
            $table->integer('idUsuario', true);
            $table->string('NomUsu1', 20);
            $table->string('NomUsu2', 20)->nullable();
            $table->string('ApeUsu1', 20);
            $table->string('ApeUsu2', 20)->nullable();
            $table->string('CorreoUsu', 50);
            $table->string('Clave', 100);
            $table->string('DireccionUsu', 50);
            $table->string('CiudadUsu', 15);
            $table->bigInteger('TelefonoUsu');
            $table->integer('FK_roles')->index('fk_usuarios_roles');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('usuarios');
    }
};
