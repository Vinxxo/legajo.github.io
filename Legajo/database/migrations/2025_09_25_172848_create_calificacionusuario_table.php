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
        Schema::create('calificacionusuario', function (Blueprint $table) {
            $table->integer('idCalificacionUsu', true);
            $table->decimal('CalificacionUsu', 10, 0)->nullable();
            $table->integer('FK_usuarios')->index('fk_calificacionusuario_usuarios');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('calificacionusuario');
    }
};
