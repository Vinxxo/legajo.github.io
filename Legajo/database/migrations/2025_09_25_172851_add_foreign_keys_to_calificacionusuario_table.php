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
        Schema::table('calificacionusuario', function (Blueprint $table) {
            $table->foreign(['FK_usuarios'], 'FK_calificacionUsuario_usuarios')->references(['idUsuario'])->on('usuarios')->onUpdate('restrict')->onDelete('restrict');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::table('calificacionusuario', function (Blueprint $table) {
            $table->dropForeign('FK_calificacionUsuario_usuarios');
        });
    }
};
