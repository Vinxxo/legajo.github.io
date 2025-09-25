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
        Schema::create('autor', function (Blueprint $table) {
            $table->integer('idAutor', true);
            $table->string('NomAutor1', 20)->nullable();
            $table->string('NomAutor2', 20)->nullable();
            $table->string('ApeAutor1', 20)->nullable();
            $table->string('ApeAutor2', 20)->nullable();
            $table->string('ApodoAutor', 45)->nullable();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('autor');
    }
};
