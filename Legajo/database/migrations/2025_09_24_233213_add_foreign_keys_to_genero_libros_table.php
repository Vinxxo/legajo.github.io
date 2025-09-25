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
        Schema::table('genero_libros', function (Blueprint $table) {
            $table->foreign(['FK_idGenero'], 'FK_generolibros_genero')->references(['idGenero'])->on('genero')->onUpdate('restrict')->onDelete('restrict');
            $table->foreign(['FK_idLibro'], 'FK_generolibros_libros')->references(['idLibro'])->on('libros')->onUpdate('restrict')->onDelete('restrict');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::table('genero_libros', function (Blueprint $table) {
            $table->dropForeign('FK_generolibros_genero');
            $table->dropForeign('FK_generolibros_libros');
        });
    }
};
