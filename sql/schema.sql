CREATE DATABASE IF NOT EXISTS evolup;
USE evolup;

CREATE TABLE USUARIO (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE CHECK (email LIKE '%@%.%'),
    contrasena VARCHAR(255) NOT NULL
);

CREATE TABLE OBJETIVO (
    id_objetivo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    fecha_inicio DATE NOT NULL,
    fecha_objetivo DATE NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'activo' CHECK (estado IN ('activo', 'completado', 'cancelado')),
    id_usuario_fk INT NOT NULL,
    FOREIGN KEY (id_usuario_fk) REFERENCES USUARIO(id_usuario) ON DELETE CASCADE,
    CHECK (fecha_objetivo <= DATE_ADD(fecha_inicio, INTERVAL 21 DAY))
);

CREATE TABLE HABITO (
    id_habito INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    frecuencia VARCHAR(20) NOT NULL CHECK (frecuencia IN ('diario', 'semanal')),
    id_objetivo_fk INT NOT NULL,
    FOREIGN KEY (id_objetivo_fk) REFERENCES OBJETIVO(id_objetivo) ON DELETE CASCADE
);

CREATE TABLE REGISTRO (
    id_registro INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    completado BOOLEAN NOT NULL DEFAULT FALSE,
    id_habito_fk INT NOT NULL,
    FOREIGN KEY (id_habito_fk) REFERENCES HABITO(id_habito) ON DELETE CASCADE,
    UNIQUE (id_habito_fk, fecha)
);

CREATE TABLE LOGRO (
    id_logro INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT
);

CREATE TABLE CATEGORIA (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE USUARIO_LOGRO (
    id_usuario_fk INT NOT NULL,
    id_logro_fk INT NOT NULL,
    fecha_obtenido DATE NOT NULL,
    PRIMARY KEY (id_usuario_fk, id_logro_fk),
    FOREIGN KEY (id_usuario_fk) REFERENCES USUARIO(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_logro_fk) REFERENCES LOGRO(id_logro) ON DELETE CASCADE
);

CREATE TABLE HABITO_CATEGORIA (
    id_habito_fk INT NOT NULL,
    id_categoria_fk INT NOT NULL,
    PRIMARY KEY (id_habito_fk, id_categoria_fk),
    FOREIGN KEY (id_habito_fk) REFERENCES HABITO(id_habito) ON DELETE CASCADE,
    FOREIGN KEY (id_categoria_fk) REFERENCES CATEGORIA(id_categoria) ON DELETE CASCADE
);
