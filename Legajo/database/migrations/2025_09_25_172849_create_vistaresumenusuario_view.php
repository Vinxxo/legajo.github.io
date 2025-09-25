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
        DB::statement("CREATE VIEW `vistaresumenusuario` AS select `u`.`idUsuario` AS `idUsuario`,`u`.`NomUsu1` AS `NomUsu1`,`u`.`ApeUsu1` AS `ApeUsu1`,`cu`.`CalificacionUsu` AS `CalificacionUsu`,count(`cu2`.`FK_idChat`) AS `TotalChats` from ((`legajobd`.`usuarios` `u` left join `legajobd`.`calificacionusuario` `cu` on(`u`.`idUsuario` = `cu`.`FK_usuarios`)) left join `legajobd`.`chats_usuarios` `cu2` on(`u`.`idUsuario` = `cu2`.`FK_idUsuario`)) group by `u`.`idUsuario`");
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        DB::statement("DROP VIEW IF EXISTS `vistaresumenusuario`");
    }
};
