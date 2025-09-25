-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema legajobd
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema legajobd
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `legajobd` DEFAULT CHARACTER SET utf8mb4 ;
USE `legajobd` ;

-- -----------------------------------------------------
-- Table `legajobd`.`autor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`autor` (
  `idAutor` INT(11) NOT NULL AUTO_INCREMENT,
  `NomAutor1` VARCHAR(20) NULL DEFAULT NULL,
  `NomAutor2` VARCHAR(20) NULL DEFAULT NULL,
  `ApeAutor1` VARCHAR(20) NULL DEFAULT NULL,
  `ApeAutor2` VARCHAR(20) NULL DEFAULT NULL,
  `ApodoAutor` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idAutor`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `legajobd`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`roles` (
  `idRol` INT(11) NOT NULL AUTO_INCREMENT,
  `rol` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`idRol`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `legajobd`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`usuarios` (
  `idUsuario` INT(11) NOT NULL AUTO_INCREMENT,
  `NomUsu1` VARCHAR(20) NOT NULL,
  `NomUsu2` VARCHAR(20) NULL DEFAULT NULL,
  `ApeUsu1` VARCHAR(20) NOT NULL,
  `ApeUsu2` VARCHAR(20) NULL DEFAULT NULL,
  `CorreoUsu` VARCHAR(50) NOT NULL,
  `Clave` VARCHAR(100) NOT NULL,
  `DireccionUsu` VARCHAR(50) NOT NULL,
  `CiudadUsu` VARCHAR(15) NOT NULL,
  `TelefonoUsu` BIGINT(20) NOT NULL,
  `FK_roles` INT(11) NOT NULL,
  PRIMARY KEY (`idUsuario`),
  INDEX `FK_usuarios_roles` (`FK_roles` ASC) VISIBLE,
  CONSTRAINT `FK_usuarios_roles`
    FOREIGN KEY (`FK_roles`)
    REFERENCES `legajobd`.`roles` (`idRol`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `legajobd`.`libros`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`libros` (
  `idLibro` INT(11) NOT NULL AUTO_INCREMENT,
  `TituloLib` VARCHAR(100) NOT NULL,
  `SinopsisLib` VARCHAR(400) NOT NULL,
  `EstadoLib` ENUM('Publicado', 'Leyendo') NOT NULL,
  `Imagen` VARCHAR(255) NOT NULL,
  `FK_usuarios` INT(11) NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT NULL,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`idLibro`),
  INDEX `FK_libros_usuarios` (`FK_usuarios` ASC) VISIBLE,
  CONSTRAINT `FK_libros_usuarios`
    FOREIGN KEY (`FK_usuarios`)
    REFERENCES `legajobd`.`usuarios` (`idUsuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `legajobd`.`autor_libros`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`autor_libros` (
  `FK_idAutor` INT(11) NOT NULL,
  `FK_idLibro` INT(11) NOT NULL,
  PRIMARY KEY (`FK_idAutor`, `FK_idLibro`),
  INDEX `FK_autorlibros_libros` (`FK_idLibro` ASC) VISIBLE,
  CONSTRAINT `FK_autorlibros_autor`
    FOREIGN KEY (`FK_idAutor`)
    REFERENCES `legajobd`.`autor` (`idAutor`),
  CONSTRAINT `FK_autorlibros_libros`
    FOREIGN KEY (`FK_idLibro`)
    REFERENCES `legajobd`.`libros` (`idLibro`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `legajobd`.`cache`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`cache` (
  `key` VARCHAR(255) NOT NULL,
  `value` MEDIUMTEXT NOT NULL,
  `expiration` INT(11) NOT NULL,
  PRIMARY KEY (`key`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `legajobd`.`cache_locks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`cache_locks` (
  `key` VARCHAR(255) NOT NULL,
  `owner` VARCHAR(255) NOT NULL,
  `expiration` INT(11) NOT NULL,
  PRIMARY KEY (`key`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `legajobd`.`calificacionlibro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`calificacionlibro` (
  `idCalificacionLib` INT(11) NOT NULL AUTO_INCREMENT,
  `CalificacionLib` DECIMAL(10,0) NULL DEFAULT NULL,
  `FK_libros` INT(11) NOT NULL,
  PRIMARY KEY (`idCalificacionLib`),
  INDEX `FK_calificacionLibro_libros` (`FK_libros` ASC) VISIBLE,
  CONSTRAINT `FK_calificacionLibro_libros`
    FOREIGN KEY (`FK_libros`)
    REFERENCES `legajobd`.`libros` (`idLibro`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `legajobd`.`calificacionusuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`calificacionusuario` (
  `idCalificacionUsu` INT(11) NOT NULL AUTO_INCREMENT,
  `CalificacionUsu` DECIMAL(10,0) NULL DEFAULT NULL,
  `FK_usuarios` INT(11) NOT NULL,
  PRIMARY KEY (`idCalificacionUsu`),
  INDEX `FK_calificacionUsuario_usuarios` (`FK_usuarios` ASC) VISIBLE,
  CONSTRAINT `FK_calificacionUsuario_usuarios`
    FOREIGN KEY (`FK_usuarios`)
    REFERENCES `legajobd`.`usuarios` (`idUsuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `legajobd`.`chats`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`chats` (
  `idChat` INT(11) NOT NULL AUTO_INCREMENT,
  `Contenido` VARCHAR(150) NULL DEFAULT NULL,
  `FechaChat` DATE NOT NULL,
  `HoraChat` DATETIME NOT NULL,
  PRIMARY KEY (`idChat`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `legajobd`.`chats_usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`chats_usuarios` (
  `FK_idChat` INT(11) NOT NULL,
  `FK_idUsuario` INT(11) NOT NULL,
  PRIMARY KEY (`FK_idChat`, `FK_idUsuario`),
  INDEX `FK_chatsusuarios_usuarios` (`FK_idUsuario` ASC) VISIBLE,
  CONSTRAINT `FK_chatsusuarios_chats`
    FOREIGN KEY (`FK_idChat`)
    REFERENCES `legajobd`.`chats` (`idChat`),
  CONSTRAINT `FK_chatsusuarios_usuarios`
    FOREIGN KEY (`FK_idUsuario`)
    REFERENCES `legajobd`.`usuarios` (`idUsuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `legajobd`.`genero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`genero` (
  `idGenero` INT(11) NOT NULL AUTO_INCREMENT,
  `GeneroLib` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idGenero`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `legajobd`.`genero_libros`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`genero_libros` (
  `FK_idGenero` INT(11) NOT NULL,
  `FK_idLibro` INT(11) NOT NULL,
  PRIMARY KEY (`FK_idGenero`, `FK_idLibro`),
  INDEX `FK_generolibros_libros` (`FK_idLibro` ASC) VISIBLE,
  CONSTRAINT `FK_generolibros_genero`
    FOREIGN KEY (`FK_idGenero`)
    REFERENCES `legajobd`.`genero` (`idGenero`),
  CONSTRAINT `FK_generolibros_libros`
    FOREIGN KEY (`FK_idLibro`)
    REFERENCES `legajobd`.`libros` (`idLibro`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `legajobd`.`migrations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`migrations` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `migration` VARCHAR(255) NOT NULL,
  `batch` INT(11) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 29
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `legajobd`.`reporteslibros`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`reporteslibros` (
  `idReporteUsu` INT(11) NOT NULL AUTO_INCREMENT,
  `FK_idLibroReportado` INT(11) NOT NULL,
  `motivo` TEXT NOT NULL,
  `fecha_reporte` DATETIME NULL DEFAULT CURRENT_TIMESTAMP(),
  `estado` ENUM('pendiente', 'revisado', 'rechazado') NULL DEFAULT 'pendiente',
  PRIMARY KEY (`idReporteUsu`),
  INDEX `FK_libro_reportado_id` (`FK_idLibroReportado` ASC) VISIBLE,
  CONSTRAINT `FK_libro_reportado_id`
    FOREIGN KEY (`FK_idLibroReportado`)
    REFERENCES `legajobd`.`libros` (`idLibro`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `legajobd`.`reportesusuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`reportesusuarios` (
  `idReporteUsu` INT(11) NOT NULL AUTO_INCREMENT,
  `FK_idUsuarioReportado` INT(11) NOT NULL,
  `motivo` TEXT NOT NULL,
  `fecha_reporte` DATETIME NULL DEFAULT CURRENT_TIMESTAMP(),
  `estado` ENUM('pendiente', 'revisado', 'rechazado') NULL DEFAULT 'pendiente',
  PRIMARY KEY (`idReporteUsu`),
  INDEX `FK_usuario_reportado_id` (`FK_idUsuarioReportado` ASC) VISIBLE,
  CONSTRAINT `FK_usuario_reportado_id`
    FOREIGN KEY (`FK_idUsuarioReportado`)
    REFERENCES `legajobd`.`usuarios` (`idUsuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `legajobd`.`sessions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`sessions` (
  `id` VARCHAR(255) NOT NULL,
  `user_id` BIGINT(20) UNSIGNED NULL DEFAULT NULL,
  `ip_address` VARCHAR(45) NULL DEFAULT NULL,
  `user_agent` TEXT NULL DEFAULT NULL,
  `payload` LONGTEXT NOT NULL,
  `last_activity` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `sessions_user_id_index` (`user_id` ASC) VISIBLE,
  INDEX `sessions_last_activity_index` (`last_activity` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

USE `legajobd` ;

-- -----------------------------------------------------
-- Placeholder table for view `legajobd`.`vistalibrosautores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`vistalibrosautores` (`TituloLib` INT, `Autor` INT, `CalificacionLib` INT);

-- -----------------------------------------------------
-- Placeholder table for view `legajobd`.`vistalibrosgenero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`vistalibrosgenero` (`TituloLib` INT, `GeneroLib` INT, `EstadoLib` INT, `CreadoPor` INT);

-- -----------------------------------------------------
-- Placeholder table for view `legajobd`.`vistaresumenusuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `legajobd`.`vistaresumenusuario` (`idUsuario` INT, `NomUsu1` INT, `ApeUsu1` INT, `CalificacionUsu` INT, `TotalChats` INT);

-- -----------------------------------------------------
-- View `legajobd`.`vistalibrosautores`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `legajobd`.`vistalibrosautores`;
USE `legajobd`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `legajobd`.`vistalibrosautores` AS select `l`.`TituloLib` AS `TituloLib`,concat(`a`.`NomAutor1`,' ',`a`.`ApeAutor1`) AS `Autor`,`cl`.`CalificacionLib` AS `CalificacionLib` from (((`legajobd`.`libros` `l` join `legajobd`.`autor_libros` `al` on(`l`.`idLibro` = `al`.`FK_idLibro`)) join `legajobd`.`autor` `a` on(`al`.`FK_idAutor` = `a`.`idAutor`)) join `legajobd`.`calificacionlibro` `cl` on(`l`.`idLibro` = `cl`.`FK_libros`));

-- -----------------------------------------------------
-- View `legajobd`.`vistalibrosgenero`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `legajobd`.`vistalibrosgenero`;
USE `legajobd`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `legajobd`.`vistalibrosgenero` AS select `l`.`TituloLib` AS `TituloLib`,`g`.`GeneroLib` AS `GeneroLib`,`l`.`EstadoLib` AS `EstadoLib`,concat(`u`.`NomUsu1`,' ',`u`.`ApeUsu1`) AS `CreadoPor` from (((`legajobd`.`libros` `l` join `legajobd`.`genero_libros` `gl` on(`l`.`idLibro` = `gl`.`FK_idLibro`)) join `legajobd`.`genero` `g` on(`gl`.`FK_idGenero` = `g`.`idGenero`)) join `legajobd`.`usuarios` `u` on(`l`.`FK_usuarios` = `u`.`idUsuario`));

-- -----------------------------------------------------
-- View `legajobd`.`vistaresumenusuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `legajobd`.`vistaresumenusuario`;
USE `legajobd`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `legajobd`.`vistaresumenusuario` AS select `u`.`idUsuario` AS `idUsuario`,`u`.`NomUsu1` AS `NomUsu1`,`u`.`ApeUsu1` AS `ApeUsu1`,`cu`.`CalificacionUsu` AS `CalificacionUsu`,count(`cu2`.`FK_idChat`) AS `TotalChats` from ((`legajobd`.`usuarios` `u` left join `legajobd`.`calificacionusuario` `cu` on(`u`.`idUsuario` = `cu`.`FK_usuarios`)) left join `legajobd`.`chats_usuarios` `cu2` on(`u`.`idUsuario` = `cu2`.`FK_idUsuario`)) group by `u`.`idUsuario`;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
