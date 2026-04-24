USE evolup;

INSERT INTO USUARIO (nombre, email, contrasena) VALUES
('Víctor', 'victor@gmail.com', 'Victor2026!'),
('Manuel', 'manuel@gmail.com', 'Manuel2026!');

INSERT INTO OBJETIVO (nombre, descripcion, fecha_inicio, fecha_objetivo, estado, id_usuario_fk) VALUES
('Aprendizaje', 'Ampliar conocimientos y habilidades nuevas', '2026-01-01', '2026-01-21', 'activo', 1),
('Mejorar salud', 'Cuidar el cuerpo y el bienestar fisico', '2026-01-15', '2026-02-05', 'activo', 1),
('Cambio de habitos', 'Adoptar una rutina de vida mas equilibrada', '2026-02-01', '2026-02-22', 'activo', 2);

INSERT INTO HABITO (nombre, frecuencia, id_objetivo_fk) VALUES
('Leer 30 minutos', 'diario', 1),
('Practicar vocabulario en ingles', 'diario', 1),
('Ver una serie en ingles', 'semanal', 1),
('Ir al gimnasio', 'diario', 2),
('Beber 2 litros de agua', 'diario', 2),
('Meditar 10 minutos', 'diario', 3),
('Dormir 8 horas', 'diario', 3);

INSERT INTO REGISTRO (fecha, completado, id_habito_fk) VALUES
('2026-01-01', TRUE, 1),
('2026-01-02', TRUE, 1),
('2026-01-03', FALSE, 1),
('2026-01-15', TRUE, 4),
('2026-01-16', TRUE, 4),
('2026-01-17', TRUE, 4),
('2026-02-01', TRUE, 6),
('2026-02-02', FALSE, 6);

INSERT INTO LOGRO (nombre, descripcion) VALUES
('Racha de 1 semana', 'Mantuviste un habito durante 7 dias seguidos'),
('Racha de 2 semanas', 'Mantuviste un habito durante 14 dias seguidos'),
('Racha de 3 semanas', 'Mantuviste un habito durante 21 dias seguidos'),
('Racha de 1 mes', 'Mantuviste un habito durante 30 dias seguidos'),
('Racha de 2 meses', 'Mantuviste un habito durante 2 meses seguidos'),
('Racha de 3 meses', 'Mantuviste un habito durante 3 meses seguidos'),
('Racha de 4 meses', 'Mantuviste un habito durante 4 meses seguidos'),
('Racha de 5 meses', 'Mantuviste un habito durante 5 meses seguidos'),
('Racha de 6 meses', 'Mantuviste un habito durante 6 meses seguidos'),
('Objetivo completado', 'Completaste uno de tus objetivos');

INSERT INTO CATEGORIA (nombre) VALUES
('Salud'),
('Educacion'),
('Deporte'),
('Bienestar'),
('Idiomas');

INSERT INTO USUARIO_LOGRO (id_usuario_fk, id_logro_fk, fecha_obtenido) VALUES
(1, 1, '2026-01-07'),
(1, 2, '2026-01-14'),
(2, 1, '2026-02-07'),
(2, 2, '2026-02-14'),
(1, 10, '2026-03-01');

INSERT INTO HABITO_CATEGORIA (id_habito_fk, id_categoria_fk) VALUES
(1, 2),
(2, 2),
(2, 5),
(3, 5),
(4, 1),
(4, 3),
(5, 1),
(5, 4),
(6, 4),
(7, 1),
(7, 4);
