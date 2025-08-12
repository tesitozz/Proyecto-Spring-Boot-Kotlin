DROP DATABASE IF EXISTS veterinaria_db;
CREATE DATABASE veterinaria_db;
USE veterinaria_db;

DROP TABLE IF EXISTS tb_administrador;
CREATE TABLE tb_administrador(
  id BIGINT NOT NULL AUTO_INCREMENT,
  correo VARCHAR(255) DEFAULT NULL,
  password VARCHAR(255) DEFAULT NULL,
  usuario VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY(id)
);

DROP TABLE IF EXISTS tb_cliente;
CREATE TABLE tb_cliente(
  id_cli BIGINT NOT NULL AUTO_INCREMENT,
  apellidos_cli VARCHAR(64) NOT NULL,
  celular_cli VARCHAR(16) NOT NULL,
  correo_cli VARCHAR(80) NOT NULL UNIQUE,
  direccion_cli VARCHAR(256) DEFAULT NULL,
  dni_cli VARCHAR(8) NOT NULL UNIQUE,
  genero_cli VARCHAR(32) DEFAULT NULL,
  nombres_cli VARCHAR(64) NOT NULL,
  PRIMARY KEY(id_cli)
);

DROP TABLE IF EXISTS tb_medico;
CREATE TABLE tb_medico(
  id_med BIGINT NOT NULL AUTO_INCREMENT,
  apellidos_med VARCHAR(64) NOT NULL,
  dni_med VARCHAR(8) NOT NULL UNIQUE,
  especialidad_med VARCHAR(64) NOT NULL,
  nombres_med VARCHAR(64) NOT NULL,
  genero_med VARCHAR(32) DEFAULT NULL,
  correo_med VARCHAR(80) NOT NULL UNIQUE,
  celular_med VARCHAR(16) NOT NULL,
  PRIMARY KEY(id_med)
);

DROP TABLE IF EXISTS tb_animales;
CREATE TABLE tb_animales(
  id_ani BIGINT NOT NULL AUTO_INCREMENT,
  color_ani VARCHAR(32) DEFAULT NULL,
  edad_ani INT NOT NULL,
  genero_ani VARCHAR(16) NOT NULL,
  nombre_ani VARCHAR(64) NOT NULL,
  peso_ani DOUBLE NOT NULL,
  raza_ani VARCHAR(64) DEFAULT NULL,
  tipo_ani VARCHAR(32) NOT NULL,
  PRIMARY KEY(id_ani)
);

DROP TABLE IF EXISTS tb_historia_clinica;
CREATE TABLE tb_historia_clinica(
  id_hc BIGINT NOT NULL AUTO_INCREMENT,
  fecha_registro_hc DATE NOT NULL,
  hora_registro_hc TIME(6) NOT NULL,
  diagnostico_hc VARCHAR(512) DEFAULT NULL,
  tratamiento_hc VARCHAR(512) DEFAULT NULL,
  vacunas_aplicadas_hc VARCHAR(512) DEFAULT NULL,
  observaciones_hc VARCHAR(512) DEFAULT NULL,
  id_ani BIGINT NOT NULL,
  id_cli BIGINT NOT NULL,
  id_med BIGINT NOT NULL,
  PRIMARY KEY(id_hc),
  FOREIGN KEY(id_ani) REFERENCES tb_animales(id_ani),
  FOREIGN KEY(id_cli) REFERENCES tb_cliente(id_cli),
  FOREIGN KEY(id_med) REFERENCES tb_medico(id_med)
);

-- Insertar en la tabla tb_cliente
INSERT INTO tb_cliente(apellidos_cli, celular_cli, correo_cli, direccion_cli, dni_cli, genero_cli, nombres_cli)
VALUES 
  ('Patiño', '987654321', 'gomez@example.com', 'Av. Siempre Viva 123', '12345678', 'Masculino', 'Juan'),
  ('Carrasco', '912345678', 'lopez@example.com', 'Jr. Las Flores 456', '87654321', 'Femenino', 'María'),
  ('Pérez', '932178945', 'perez@example.com', 'Calle Luna 789', '56781234', 'Masculino', 'Carlos'),
  ('Rodríguez', '945678123', 'rodriguez@example.com', 'Calle Sol 101', '34567812', 'Femenino', 'Lucía'),
  ('Martínez', '954321876', 'martinez@example.com', 'Av. Primavera 12', '45678912', 'Masculino', 'Pedro'),
  ('Fernández', '987654987', 'fernandez@example.com', 'Jr. Los Álamos 89', '67891234', 'Femenino', 'Ana'),
  ('García', '989123456', 'garcia@example.com', 'Av. Central 67', '78912345', 'Masculino', 'Luis'),
  ('Hernández', '954687321', 'hernandez@example.com', 'Jr. Alameda 54', '89123456', 'Femenino', 'Sofía'),
  ('Ramírez', '987321456', 'ramirez@example.com', 'Calle Robles 45', '23456789', 'Masculino', 'Jorge'),
  ('Torres', '964312578', 'torres@example.com', 'Jr. Las Rosas 123', '34567891', 'Femenino', 'Camila');

-- Insertar en la tabla tb_medico
INSERT INTO tb_medico(apellidos_med, dni_med, especialidad_med, nombres_med, genero_med, correo_med, celular_med)
VALUES 
  ('Sánchez', '12345670', 'Medicina general', 'Alberto', 'Masculino', 'alberto.sanchez@example.com', '987654321'),
  ('Castro', '22345671', 'Oftalmología', 'Carla', 'Femenino', 'carla.castro@example.com', '912345678'),
  ('Reyes', '32345672', 'Animales exóticos', 'Miguel', 'Masculino', 'miguel.reyes@example.com', '932178945'),
  ('Vega', '42345673', 'Cuidados intensivos', 'Claudia', 'Femenino', 'claudia.vega@example.com', '945678123'),
  ('Jiménez', '52345674', 'Quimioterapia', 'Ricardo', 'Masculino', 'ricardo.jimenez@example.com', '954321876'),
  ('Ruiz', '62345675', 'Radiología', 'Paola', 'Femenino', 'paola.ruiz@example.com', '987654987'),
  ('Herrera', '72345676', 'Traumatología', 'Julio', 'Masculino', 'julio.herrera@example.com', '989123456'),
  ('Mendoza', '82345677', 'Cirugía general', 'Andrea', 'Femenino', 'andrea.mendoza@example.com', '954687321'),
  ('Cruz', '92345678', 'Medicina general', 'Luis', 'Masculino', 'luis.cruz@example.com', '987321456'),
  ('Morales', '03345679', 'Cuidados intensivos', 'Mónica', 'Femenino', 'monica.morales@example.com', '964312578');

-- Insertar en la tabla tb_animales
INSERT INTO tb_animales(color_ani, edad_ani, genero_ani, nombre_ani, peso_ani, raza_ani, tipo_ani)
VALUES 
  ('Blanco', 3, 'Macho', 'Snow', 5.2, 'Persa', 'Gato'),
  ('Negro', 2, 'Hembra', 'Luna', 4.8, 'Siames', 'Gato'),
  ('Marrón', 5, 'Macho', 'Max', 20.5, 'Golden Retriever', 'Perro'),
  ('Gris', 1, 'Hembra', 'Nube', 1.3, 'Cobaya', 'Roedor'),
  ('Manchado', 4, 'Macho', 'Rocky', 25.0, 'Pitbull', 'Perro'),
  ('Blanco y Negro', 2, 'Hembra', 'Mimi', 3.5, 'Angora', 'Gato'),
  ('Dorado', 6, 'Macho', 'Buddy', 22.0, 'Labrador', 'Perro'),
  ('Atigrado', 3, 'Hembra', 'Kira', 4.0, 'Bengala', 'Gato'),
  ('Negro', 2, 'Macho', 'Tom', 8.0, 'Rottweiler', 'Perro'),
  ('Blanco', 7, 'Hembra', 'Daisy', 1.1, 'Conejo', 'Roedor');

-- Insertar en la tabla tb_historia_clinica
INSERT INTO tb_historia_clinica(fecha_registro_hc, hora_registro_hc, diagnostico_hc, tratamiento_hc, vacunas_aplicadas_hc, observaciones_hc, id_ani, id_cli, id_med)
VALUES 
  ('2024-01-01', '10:30:00', 'Diagnóstico 1', 'Tratamiento 1', 'Vacunas 1', 'Observación 1', 1, 1, 1),
  ('2024-01-02', '11:00:00', 'Diagnóstico 2', 'Tratamiento 2', 'Vacunas 2', 'Observación 2', 2, 2, 2),
  ('2024-01-03', '14:15:00', 'Diagnóstico 3', 'Tratamiento 3', 'Vacunas 3', 'Observación 3', 3, 3, 3),
  ('2024-01-04', '09:45:00', 'Diagnóstico 4', 'Tratamiento 4', 'Vacunas 4', 'Observación 4', 4, 4, 4),
  ('2024-01-05', '16:00:00', 'Diagnóstico 5', 'Tratamiento 5', 'Vacunas 5', 'Observación 5', 5, 5, 5),
  ('2024-01-06', '10:20:00', 'Diagnóstico 6', 'Tratamiento 6', 'Vacunas 6', 'Observación 6', 6, 6, 6),
  ('2024-01-07', '13:30:00', 'Diagnóstico 7', 'Tratamiento 7', 'Vacunas 7', 'Observación 7', 7, 7, 7),
  ('2024-01-08', '08:00:00', 'Diagnóstico 8', 'Tratamiento 8', 'Vacunas 8', 'Observación 8', 8, 8, 8),
  ('2024-01-09', '17:00:00', 'Diagnóstico 9', 'Tratamiento 9', 'Vacunas 9', 'Observación 9', 9, 9, 9),
  ('2024-01-10', '12:45:00', 'Diagnóstico 10', 'Tratamiento 10', 'Vacunas 10', 'Observación 10', 10, 10, 10);
