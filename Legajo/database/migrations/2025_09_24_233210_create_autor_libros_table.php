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
        Schema::create('autor_libros', function (Blueprint $table) {
            $table->integer('FK_idAutor');
            $table->integer('FK_idLibro')->index('fk_autorlibros_libros');

            $table->primary(['FK_idAutor', 'FK_idLibro']);
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('autor_libros');
    }
};
