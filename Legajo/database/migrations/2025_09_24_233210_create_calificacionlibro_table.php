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
        Schema::create('calificacionlibro', function (Blueprint $table) {
            $table->integer('idCalificacionLib', true);
            $table->decimal('CalificacionLib', 10, 0)->nullable();
            $table->integer('FK_libros')->index('fk_calificacionlibro_libros');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('calificacionlibro');
    }
};
