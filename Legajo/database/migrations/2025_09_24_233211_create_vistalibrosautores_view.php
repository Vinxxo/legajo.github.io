<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Support\Facades\DB;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        DB::statement("CREATE VIEW `vistalibrosautores` AS select `l`.`TituloLib` AS `TituloLib`,concat(`a`.`NomAutor1`,' ',`a`.`ApeAutor1`) AS `Autor`,`cl`.`CalificacionLib` AS `CalificacionLib` from (((`legajobd`.`libros` `l` join `legajobd`.`autor_libros` `al` on(`l`.`idLibro` = `al`.`FK_idLibro`)) join `legajobd`.`autor` `a` on(`al`.`FK_idAutor` = `a`.`idAutor`)) join `legajobd`.`calificacionlibro` `cl` on(`l`.`idLibro` = `cl`.`FK_libros`))");
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        DB::statement("DROP VIEW IF EXISTS `vistalibrosautores`");
    }
};
