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
        Schema::table('autor_libros', function (Blueprint $table) {
            $table->foreign(['FK_idAutor'], 'FK_autorlibros_autor')->references(['idAutor'])->on('autor')->onUpdate('restrict')->onDelete('restrict');
            $table->foreign(['FK_idLibro'], 'FK_autorlibros_libros')->references(['idLibro'])->on('libros')->onUpdate('restrict')->onDelete('restrict');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::table('autor_libros', function (Blueprint $table) {
            $table->dropForeign('FK_autorlibros_autor');
            $table->dropForeign('FK_autorlibros_libros');
        });
    }
};
