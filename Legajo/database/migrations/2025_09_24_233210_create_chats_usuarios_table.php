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
        Schema::create('chats_usuarios', function (Blueprint $table) {
            $table->integer('FK_idChat');
            $table->integer('FK_idUsuario')->index('fk_chatsusuarios_usuarios');

            $table->primary(['FK_idChat', 'FK_idUsuario']);
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('chats_usuarios');
    }
};
