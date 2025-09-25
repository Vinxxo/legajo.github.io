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
        Schema::create('genero_libros', function (Blueprint $table) {
            $table->integer('FK_idGenero');
            $table->integer('FK_idLibro')->index('fk_generolibros_libros');

            $table->primary(['FK_idGenero', 'FK_idLibro']);
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('genero_libros');
    }
};
