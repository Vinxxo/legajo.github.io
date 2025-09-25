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
        Schema::table('reporteslibros', function (Blueprint $table) {
            $table->foreign(['FK_idLibroReportado'], 'FK_libro_reportado_id')->references(['idLibro'])->on('libros')->onUpdate('restrict')->onDelete('restrict');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::table('reporteslibros', function (Blueprint $table) {
            $table->dropForeign('FK_libro_reportado_id');
        });
    }
};
