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

insert into autor (NomAutor1, NomAutor2, ApeAutor1, ApeAutor2, ApodoAutor) values
('Gabriel', 'José', 'García', 'Márquez', 'Gabo'),
('Isabel', null, 'Allende', 'Llona', null),
('Jorge', 'Luis', 'Borges', null, null),
('Julio', null, 'Cortázar', null, null),
('Mario', null, 'Vargas', 'Llosa', null),
('Laura', null, 'Esquivel', null, null),
('Carlos', null, 'Fuentes', null, null),
('Pablo', null, 'Neruda', null, 'Neftalí'),
('Octavio', null, 'Paz', null, null),
('Juan', 'Carlos', 'Onetti', null, null);

insert into usuarios (NomUsu1, NomUsu2, ApeUsu1, ApeUsu2, CorreoUsu, Clave, DireccionUsu, CiudadUsu, TelefonoUsu, FK_roles) values
('Ana', null, 'Pérez', null, 'ana@example.com', 'clave123', 'Calle 1', 'Madrid', 600123456, 2),
('Luis', 'Miguel', 'Gómez', 'López', 'luis@example.com', 'pass4567', 'Calle 2', 'Barcelona', 600234567, 2),
('Elena', null, 'Martínez', 'Ruiz', 'elena@example.com', '12345678', 'Calle 3', 'Valencia', 600345678, 2),
('Carlos', null, 'Fernández', null, 'carlos@example.com', 'qwerty12', 'Calle 4', 'Sevilla', 600456789, 2),
('Lucía', 'María', 'Díaz', null, 'lucia@example.com', 'lucia123', 'Calle 5', 'Zaragoza', 600567890, 2),
('Pedro', null, 'Sánchez', null, 'pedro@example.com', 'clave456', 'Calle 6', 'Bilbao', 600678901, 2),
('Sara', null, 'Romero', null, 'sara@example.com', 'roma2020', 'Calle 7', 'Granada', 600789012, 2),
('Marta', null, 'García', null, 'marta@example.com', 'mar12345', 'Calle 8', 'Málaga', 600890123, 2),
('Diego', null, 'Vega', null, 'diego@example.com', 'diego321', 'Calle 9', 'Alicante', 600901234, 2),
('Nuria', null, 'López', null, 'nuria@example.com', 'nuria789', 'Calle 10', 'Oviedo', 600012345, 2);

insert into libros (TituloLib, SinopsisLib, EstadoLib, FK_usuarios) values
('Cien años de soledad', 'Una historia mágica en Macondo', 'Publicado', 1),
('La casa de los espíritus', 'Saga familiar con tintes mágicos', 'Publicado', 2),
('Ficciones', 'Colección de cuentos filosóficos', 'Publicado', 3),
('Rayuela', 'Narrativa no lineal de una vida', 'Leyendo', 4),
('La ciudad y los perros', 'Vida en una escuela militar', 'Publicado', 5),
('Como agua para chocolate', 'Amor y cocina', 'Leyendo', 6),
('Terra Nostra', 'Novela barroca histórica', 'Publicado', 7),
('Veinte poemas de amor', 'Versos románticos', 'Publicado', 8),
('El laberinto de la soledad', 'Reflexión sobre identidad mexicana', 'Leyendo', 9),
('El astillero', 'Novela existencialista', 'Publicado', 10);

insert into calificacionUsuario (CalificacionUsu, FK_usuarios) values
(4.5, 1),
(3.0, 2),
(5.0, 3),
(2.5, 4),
(4.0, 5),
(3.8, 6),
(4.7, 7),
(3.3, 8),
(4.1, 9),
(2.9, 10);

insert into calificacionLibro (CalificacionLib, FK_libros) values
(4.9, 1),
(4.2, 2),
(4.8, 3),
(3.9, 4),
(4.1, 5),
(3.7, 6),
(4.3, 7),
(4.6, 8),
(4.0, 9),
(3.5, 10);
insert into calificacionLibro (CalificacionLib, FK_libros) values
(4.8, 1),
(4.0, 2),
(4.9, 3),
(5.0, 4),
(4.9, 5),
(3.9, 6),
(3.4, 7),
(2.3, 8),
(4.6, 9),
(3.2, 10);



insert into chats (Contenido, FechaChat, HoraChat) values
('Hola, ¿cómo estás?', '2024-05-01', '2024-05-01 10:00:00'),
('Estoy leyendo un gran libro.', '2024-05-02', '2024-05-02 11:00:00'),
('¿Recomiendas algo?', '2024-05-03', '2024-05-03 12:00:00'),
('Claro, te paso el nombre.', '2024-05-04', '2024-05-04 13:00:00'),
('Gracias.', '2024-05-05', '2024-05-05 14:00:00'),
('¿Ya leíste el nuevo?', '2024-05-06', '2024-05-06 15:00:00'),
('No, ¿vale la pena?', '2024-05-07', '2024-05-07 16:00:00'),
('Mucho, es muy bueno.', '2024-05-08', '2024-05-08 17:00:00'),
('Lo buscaré.', '2024-05-09', '2024-05-09 18:00:00'),
('¡Nos vemos!', '2024-05-10', '2024-05-10 19:00:00');

insert into chats_usuarios (FK_idChat, FK_idUsuario) values
(1, 1),
(2, 2),
(3, 1),
(4, 2),
(5, 3),
(6, 4),
(7, 5),
(8, 6),
(9, 7),
(10, 8);

insert into genero_libros (FK_idGenero, FK_idLibro) values
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);

insert into autor_libros (FK_idAutor, FK_idLibro) values
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);

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