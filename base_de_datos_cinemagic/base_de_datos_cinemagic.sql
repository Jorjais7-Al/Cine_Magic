CREATE DATABASE cinemagic;
USE cinemagic;

CREATE TABLE peliculas (
	id_pelicula INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR (50) NOT NULL,
    duracion INT
);

CREATE TABLE funciones (
	id_funcion INT PRIMARY KEY AUTO_INCREMENT,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    id_pelicula INT NOT NULL,
    FOREIGN KEY (id_pelicula) REFERENCES peliculas(id_pelicula)
);

CREATE TABLE boletos (
	id_boleto INT PRIMARY KEY AUTO_INCREMENT,
    asiento VARCHAR(10) NOT NULL,
    precio DECIMAL(6,2) NOT NULL CHECK(precio>=0),
    id_funcion INT NOT NULL,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_funcion) REFERENCES funciones(id_funcion)
);

CREATE TABLE usuarios (
	id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR (50) NOT NULL,
    correo VARCHAR(100) UNIQUE,
	tipo ENUM('ESPECTADOR', 'ADMINISTRADOR')
);

CREATE TABLE calificaciones (
	id_calificacion INT PRIMARY KEY AUTO_INCREMENT,
    calificacion INT CHECK (calificacion BETWEEN 1 AND 5),
    resenia VARCHAR (100),
    id_usuario INT NOT NULL,
    id_pelicula INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_pelicula) REFERENCES peliculas(id_pelicula)
);
