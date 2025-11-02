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

-- Tabla de secuencias por abreviación
CREATE TABLE seq_tipo (
  tipo ENUM('LIB','REV','CDA','DVD') PRIMARY KEY,
  siguiente INT NOT NULL
);

INSERT INTO seq_tipo VALUES ('LIB',0),('REV',0),('CDA',0),('DVD',0);

-- Trigger que mapea 'LIBRO'->'LIB', 'REVISTA'->'REV', 'CD'->'CDA', 'DVD'->'DVD'
DROP TRIGGER IF EXISTS trg_material_bi;
DELIMITER //
CREATE TRIGGER trg_material_bi
BEFORE INSERT ON material
FOR EACH ROW
BEGIN
  DECLARE v_abrev CHAR(3);

  -- Mapear tipo largo a abreviatura usada por seq_tipo y por codigo_interno
  CASE NEW.tipo
    WHEN 'LIBRO'   THEN SET v_abrev := 'LIB';
    WHEN 'REVISTA' THEN SET v_abrev := 'REV';
    WHEN 'CD'      THEN SET v_abrev := 'CDA';
    WHEN 'DVD'     THEN SET v_abrev := 'DVD';
    ELSE
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Tipo inválido';
  END CASE;

  -- Incrementar correlativo por abreviación y generar código interno
  UPDATE seq_tipo
     SET siguiente = LAST_INSERT_ID(siguiente + 1)
   WHERE tipo = v_abrev;

  SET NEW.codigo_interno = CONCAT(v_abrev, LPAD(LAST_INSERT_ID(), 5, '0'));
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
