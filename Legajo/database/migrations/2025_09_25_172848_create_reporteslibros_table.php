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
        Schema::create('reporteslibros', function (Blueprint $table) {
            $table->integer('idReporteUsu', true);
            $table->integer('FK_idLibroReportado')->index('fk_libro_reportado_id');
            $table->text('motivo');
            $table->dateTime('fecha_reporte')->nullable()->useCurrent();
            $table->enum('estado', ['pendiente', 'revisado', 'rechazado'])->nullable()->default('pendiente');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('reporteslibros');
    }
};
