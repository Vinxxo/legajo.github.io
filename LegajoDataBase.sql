create database LegajoBD;
use LegajoBD;

create table roles(
	idRol int not null auto_increment,
    rol varchar(15) not null,
    primary key (idRol)
);

create table chats(
	idChat int not null auto_increment,
    Contenido varchar(150),
    FechaChat date not null,
    HoraChat datetime not null,
    primary key (idChat)
);

create table genero(
	idGenero int not null auto_increment,
    GeneroLib varchar(45) not null,
    primary key (idGenero)
);

create table autor(
	idAutor int not null auto_increment,
    NomAutor1 varchar(20),
    NomAutor2 varchar(20),
    ApeAutor1 varchar(20),
    ApeAutor2 varchar(20),
    ApodoAutor varchar(45),
    primary key (idAutor)
);

create table usuarios(
	idUsuario int not null auto_increment,
    NomUsu1 varchar(20) not null,
    NomUsu2 varchar(20),
    ApeUsu1 varchar(20) not null,
    ApeUsu2 varchar(20),
    CorreoUsu varchar(50) not null,
    Clave varchar(100) not null,
    DireccionUsu varchar(50) not null,
    CiudadUsu varchar(15) not null,
    TelefonoUsu bigint not null,
    FK_roles int not null,
    primary key (idUsuario)
);

create table libros(
	idLibro int not null auto_increment,
    TituloLib varchar(100) not null,
    SinopsisLib varchar(400) not null,
    EstadoLib enum('Publicado', 'Leyendo') not null,
    Imagen varchar(255) not null,
    FK_usuarios int not null,
    primary key (idLibro)
);

create table calificacionUsuario(
	idCalificacionUsu int not null auto_increment,
    CalificacionUsu decimal,
    FK_usuarios int not null,
    primary key (idCalificacionUsu)
);

create table calificacionLibro(
	idCalificacionLib int not null auto_increment,
    CalificacionLib decimal,
    FK_libros int not null,
    primary key (idCalificacionLib)
);

create table chats_usuarios (
	FK_idChat int not null,
    FK_idUsuario int not null,
    primary key (FK_idChat, FK_idUsuario)
);

create table genero_libros (
	FK_idGenero int not null,
    FK_idLibro int not null,
    primary key (FK_idGenero, FK_idLibro)
);

create table autor_libros (
	FK_idAutor int not null,
    FK_idLibro int not null,
    primary key (FK_idAutor, FK_idLibro)
);

CREATE TABLE reportesUsuarios (
    idReporteUsu INT AUTO_INCREMENT PRIMARY KEY,
    FK_idUsuarioReportado INT not null,
    motivo TEXT NOT NULL,
    fecha_reporte DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('pendiente', 'revisado', 'rechazado') DEFAULT 'pendiente'
);

CREATE TABLE reportesLibros (
    idReporteUsu INT AUTO_INCREMENT PRIMARY KEY,
    FK_idLibroReportado INT not null,
    motivo TEXT NOT NULL,
    fecha_reporte DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('pendiente', 'revisado', 'rechazado') DEFAULT 'pendiente'
);


alter table reportesUsuarios
add constraint FK_usuario_reportado_id
foreign key (FK_idUsuarioReportado) references usuarios (idUsuario);

alter table reportesLibros
add constraint FK_libro_reportado_id
foreign key (FK_idLibroReportado) references libros (idLibro);

alter table libros
add constraint FK_libros_usuarios
foreign key (FK_usuarios) references usuarios (idUsuario);

alter table calificacionLibro
add constraint FK_calificacionLibro_libros
foreign key (FK_libros) references libros (idLibro);

alter table autor_libros
add constraint FK_autorlibros_libros
foreign key (FK_idLibro) references libros (idLibro);

alter table autor_libros
add constraint FK_autorlibros_autor
foreign key (FK_idAutor) references autor (idAutor);

alter table genero_libros
add constraint FK_generolibros_libros
foreign key (FK_idLibro) references libros (idLibro);

alter table genero_libros
add constraint FK_generolibros_genero
foreign key (FK_idGenero) references genero (idGenero);

alter table usuarios
add constraint FK_usuarios_roles
foreign key (FK_roles) references roles (idRol);

alter table calificacionUsuario
add constraint FK_calificacionUsuario_usuarios
foreign key (FK_usuarios) references usuarios (idUsuario); 

alter table chats_usuarios
add constraint FK_chatsusuarios_usuarios
foreign key (FK_idUsuario) references usuarios (idUsuario);

alter table chats_usuarios
add constraint FK_chatsusuarios_chats
foreign key (FK_idChat) references chats (idChat);


insert into roles (rol) values 
('Administrador'),
('Cliente');

insert into genero (GeneroLib) values
('Ficción'),
('No Ficción'),
('Fantasía'),
('Ciencia Ficción'),
('Romance'),
('Terror'),
('Misterio'),
('Aventura'),
('Histórico'),
('Biografía');


-- CONSULTAS ANIDADAS
--  Usuarios que calificaron por encima de 4 de todos los usuarios
SELECT 
    u.idUsuario,
    u.NomUsu1,
    u.ApeUsu1,
    cu.CalificacionUsu
FROM usuarios u
JOIN calificacionUsuario cu ON u.idUsuario = cu.FK_usuarios
WHERE cu.CalificacionUsu > 4;

--  Libros con calificaciones superiores a 4
SELECT 
    l.idLibro,
    l.TituloLib,
    cl.CalificacionLib
FROM libros l
JOIN calificacionLibro cl ON l.idLibro = cl.FK_libros
WHERE cl.CalificacionLib > 4;

-- libros publicados
SELECT 
    u.idUsuario,
    u.NomUsu1,
    u.ApeUsu1,
    cu.CalificacionUsu
FROM usuarios u
JOIN calificacionUsuario cu ON u.idUsuario = cu.FK_usuarios
WHERE cu.CalificacionUsu > 4;
SELECT 
    l.idLibro,
    l.TituloLib,
    cl.CalificacionLib
FROM libros l
JOIN calificacionLibro cl ON l.idLibro = cl.FK_libros
WHERE cl.CalificacionLib > 4;

-- Autores
SELECT 
    l.idLibro,
    l.TituloLib AS Libro,
    a.NomAutor1 AS NombreAutor,
    a.ApeAutor1 AS ApellidoAutor,
    g.GeneroLib AS Genero,
    l.EstadoLib AS Estado,
    u.NomUsu1 AS NombreUsuario,
    u.ApeUsu1 AS ApellidoUsuario
FROM libros l
INNER JOIN autor_libros al ON l.idLibro = al.FK_idLibro
INNER JOIN autor a ON al.FK_idAutor = a.idAutor
INNER JOIN genero_libros gl ON l.idLibro = gl.FK_idLibro
INNER JOIN genero g ON gl.FK_idGenero = g.idGenero
INNER JOIN usuarios u ON l.FK_usuarios = u.idUsuario;

-- CONSULTAS CON GROUP BY
-- Total de chats por usuario
SELECT u.NomUsu1, COUNT(cu.FK_idChat) AS TotalChats
FROM usuarios u
JOIN chats_usuarios cu ON u.idUsuario = cu.FK_idUsuario
GROUP BY u.idUsuario;

-- Cantidad de libros por género
SELECT g.GeneroLib, COUNT(gl.FK_idLibro) AS CantidadLibros
FROM genero g
JOIN genero_libros gl ON g.idGenero = gl.FK_idGenero
GROUP BY g.idGenero;

-- 👓 VISTAS ÚTILES
-- Vista de resumen de usuarios con calificación y número de chats
CREATE VIEW VistaResumenUsuario AS
SELECT 
    u.idUsuario,
    u.NomUsu1,
    u.ApeUsu1,
    cu.CalificacionUsu,
    COUNT(cu2.FK_idChat) AS TotalChats
FROM usuarios u
LEFT JOIN calificacionUsuario cu ON u.idUsuario = cu.FK_usuarios
LEFT JOIN chats_usuarios cu2 ON u.idUsuario = cu2.FK_idUsuario
GROUP BY u.idUsuario;

-- Vista de libros con sus autores y calificación
CREATE VIEW VistaLibrosAutores AS
SELECT 
    l.TituloLib,
    CONCAT(a.NomAutor1, ' ', a.ApeAutor1) AS Autor,
    cl.CalificacionLib
FROM libros l
JOIN autor_libros al ON l.idLibro = al.FK_idLibro
JOIN autor a ON al.FK_idAutor = a.idAutor
JOIN calificacionLibro cl ON l.idLibro = cl.FK_libros;

-- Vista de libros por género con estado y usuario creador
CREATE VIEW VistaLibrosGenero AS
SELECT 
    l.TituloLib,
    g.GeneroLib,
    l.EstadoLib,
    CONCAT(u.NomUsu1, ' ', u.ApeUsu1) AS CreadoPor
FROM libros l
JOIN genero_libros gl ON l.idLibro = gl.FK_idLibro
JOIN genero g ON gl.FK_idGenero = g.idGenero
JOIN usuarios u ON l.FK_usuarios = u.idUsuario;


DROP ROLE 'Analista_lectura';

-- Administrador de base de datos 
create role 'Administrador_BD' ;
grant select on legajobd . * to 'Administrador_BD';
create user 'David_Rodriguez'@'localhost' identified by '123clave';
grant 'Administrador_BD' to 'David_Rodriguez'@'localhost';

-- Desarrollador Backend
create role 'Desarrollador_Backend' ;
grant select on legajobd . * to 'Desarrollador_Backend';
create user 'Santiago_Duran'@'localhost' identified by 'clave321';
grant 'Desarrollador_Backend' to 'Santiago_Duran'@'localhost';

SELECT * FROM mysql.roles_mapping;

DROP PROCEDURE IF EXISTS sp_registrar_libro;



-- Creacion del procedimiento almacenado 

DELIMITER $$

CREATE PROCEDURE sp_registrar_libro(
    IN p_titulo VARCHAR(100),
    IN p_sinopsis VARCHAR(400),
    IN p_estado VARCHAR(20),  -- CAMBIO AQUÍ: VARCHAR en lugar de ENUM
    IN p_id_usuario INT,
    IN p_id_autor INT,
    IN p_id_genero INT,
    OUT p_id_nuevo_libro INT
)
BEGIN
    -- Iniciar transacción
    START TRANSACTION;

    -- Insertar el nuevo libro
    INSERT INTO libros (TituloLib, SinopsisLib, EstadoLib, FK_usuarios)
    VALUES (p_titulo, p_sinopsis, p_estado, p_id_usuario);

    -- Obtener el ID del nuevo libro
    SET p_id_nuevo_libro = LAST_INSERT_ID();

    -- Asociar el libro al autor
    INSERT INTO autor_libros (FK_idAutor, FK_idLibro)
    VALUES (p_id_autor, p_id_nuevo_libro);

    -- Asociar el libro al género
    INSERT INTO genero_libros (FK_idGenero, FK_idLibro)
    VALUES (p_id_genero, p_id_nuevo_libro);

    -- Confirmar transacción
    COMMIT;
END$$

DELIMITER ;

CALL sp_registrar_libro(
    'Habitos atomicos',
    'Creacion de habitos',
    'leyendo',
    6,  -- ID de usuario
    5,  -- ID de autor
    4,  -- ID de género
    @id_nuevo_libro
);
SELECT @id_nuevo_libro;

delete from libros
 where idLibro = 11;

select * from VistaLibrosAutores;