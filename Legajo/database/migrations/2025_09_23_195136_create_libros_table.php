<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Ejecutar las migraciones.
     */
    public function up(): void
{
    Schema::create('libros', function (Blueprint $table) {
    $table->id('idLibro');
    $table->string('TituloLib', 100);
    $table->string('SinopsisLib', 400);
    $table->enum('EstadoLib', ['Publicado', 'Leyendo']);
    $table->integer('FK_usuarios');
    $table->string('imagen')->nullable();
    $table->timestamps();

    $table->foreign('FK_usuarios')
          ->references('idUsuario')
          ->on('usuarios');
})->collation('utf8mb4_general_ci'); // 👈 fuerza collation

}


    /**
     * Revertir las migraciones.
     */
    public function down(): void
    {
        Schema::dropIfExists('libros');
    }
};
