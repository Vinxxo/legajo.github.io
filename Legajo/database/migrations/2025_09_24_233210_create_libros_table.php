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
        Schema::create('libros', function (Blueprint $table) {
            $table->integer('idLibro', true);
            $table->string('TituloLib', 100);
            $table->string('SinopsisLib', 400);
            $table->enum('EstadoLib', ['Publicado', 'Leyendo']);
            $table->integer('FK_usuarios')->index('fk_libros_usuarios');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('libros');
    }
};
