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
        DB::statement("CREATE VIEW `vistalibrosgenero` AS select `l`.`TituloLib` AS `TituloLib`,`g`.`GeneroLib` AS `GeneroLib`,`l`.`EstadoLib` AS `EstadoLib`,concat(`u`.`NomUsu1`,' ',`u`.`ApeUsu1`) AS `CreadoPor` from (((`legajobd`.`libros` `l` join `legajobd`.`genero_libros` `gl` on(`l`.`idLibro` = `gl`.`FK_idLibro`)) join `legajobd`.`genero` `g` on(`gl`.`FK_idGenero` = `g`.`idGenero`)) join `legajobd`.`usuarios` `u` on(`l`.`FK_usuarios` = `u`.`idUsuario`))");
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        DB::statement("DROP VIEW IF EXISTS `vistalibrosgenero`");
    }
};
