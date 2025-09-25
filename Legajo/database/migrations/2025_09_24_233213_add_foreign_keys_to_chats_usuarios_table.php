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
        Schema::table('chats_usuarios', function (Blueprint $table) {
            $table->foreign(['FK_idChat'], 'FK_chatsusuarios_chats')->references(['idChat'])->on('chats')->onUpdate('restrict')->onDelete('restrict');
            $table->foreign(['FK_idUsuario'], 'FK_chatsusuarios_usuarios')->references(['idUsuario'])->on('usuarios')->onUpdate('restrict')->onDelete('restrict');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::table('chats_usuarios', function (Blueprint $table) {
            $table->dropForeign('FK_chatsusuarios_chats');
            $table->dropForeign('FK_chatsusuarios_usuarios');
        });
    }
};
