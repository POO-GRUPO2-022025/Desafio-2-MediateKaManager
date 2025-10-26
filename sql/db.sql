DROP DATABASE IF EXISTS mediateca;
CREATE DATABASE mediateca;
USE mediateca;

-- Tabla principal
CREATE TABLE material (
                          id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                          codigo_interno VARCHAR(16) NOT NULL,
                          tipo ENUM('LIBRO','REVISTA','CD','DVD') NOT NULL,
                          titulo VARCHAR(255) NOT NULL,
                          unidades_disponibles INT NOT NULL DEFAULT 0,
                          CONSTRAINT pk_material PRIMARY KEY (id),
                          CONSTRAINT uq_material_codigo UNIQUE (codigo_interno)
);

CREATE TABLE seq_tipo (
                          tipo ENUM('LIBRO','REVISTA','CD','DVD') PRIMARY KEY,
                          siguiente INT NOT NULL
);

INSERT INTO seq_tipo VALUES ('LIBRO',0),('REVISTA',0),('CD',0),('DVD',0);

DELIMITER //
CREATE TRIGGER trg_material_bi
    BEFORE INSERT ON material
    FOR EACH ROW
BEGIN
    UPDATE seq_tipo
    SET siguiente = LAST_INSERT_ID(siguiente + 1)
    WHERE tipo = NEW.tipo;
    SET NEW.codigo_interno = CONCAT(NEW.tipo, LPAD(LAST_INSERT_ID(), 5, '0'));
END//
DELIMITER ;

-- Subtabla: LIBRO
CREATE TABLE libro (
                       id_material BIGINT UNSIGNED NOT NULL,
                       autor VARCHAR(255) NOT NULL,
                       numero_paginas INT NOT NULL,
                       editorial VARCHAR(255) NOT NULL,
                       isbn VARCHAR(20) NOT NULL,
                       anio_publicacion YEAR NOT NULL,

                       CONSTRAINT pk_libro PRIMARY KEY (id_material),
                       CONSTRAINT fk_libro_material
                           FOREIGN KEY (id_material) REFERENCES material(id)
);

-- Subtabla: REVISTA
CREATE TABLE revista (
                         id_material BIGINT UNSIGNED NOT NULL,
                         editorial VARCHAR(255) NOT NULL,
                         periodicidad VARCHAR(50) NOT NULL,
                         fecha_publicacion DATE NOT NULL,

                         CONSTRAINT pk_revista PRIMARY KEY (id_material),
                         CONSTRAINT fk_revista_material
                             FOREIGN KEY (id_material) REFERENCES material(id)
);

-- Subtabla: CD DE AUDIO
CREATE TABLE cd_audio (
                          id_material BIGINT UNSIGNED NOT NULL,
                          artista VARCHAR(255) NOT NULL,
                          genero VARCHAR(100) NOT NULL,
                          duracion_min INT NOT NULL,
                          numero_canciones INT NOT NULL,
                          CONSTRAINT pk_cd_audio PRIMARY KEY (id_material),
                          CONSTRAINT fk_cd_audio_material
                              FOREIGN KEY (id_material) REFERENCES material(id)
);

-- Subtabla: DVD
CREATE TABLE dvd (
                     id_material BIGINT UNSIGNED NOT NULL,
                     director VARCHAR(255) NOT NULL,
                     genero VARCHAR(100) NOT NULL,
                     duracion_min INT NOT NULL,
                     CONSTRAINT pk_dvd PRIMARY KEY (id_material),
                     CONSTRAINT fk_dvd_material
                         FOREIGN KEY (id_material) REFERENCES material(id)

);