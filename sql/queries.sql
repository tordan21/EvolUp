USE evolup;

-- 1. Todos los usuarios registrados
SELECT id_usuario, nombre, email
FROM USUARIO;

-- 2. Objetivos de un usuario concreto (Víctor, id=1)
SELECT id_objetivo, nombre, descripcion, fecha_inicio, fecha_objetivo, estado
FROM OBJETIVO
WHERE id_usuario_fk = 1;

-- 3. Hábitos con el nombre de su objetivo
SELECT h.id_habito, h.nombre AS habito, h.frecuencia, o.nombre AS objetivo
FROM HABITO h
JOIN OBJETIVO o ON h.id_objetivo_fk = o.id_objetivo;

-- 4. Hábitos de un usuario concreto (Manuel, id=2)
SELECT u.nombre AS usuario, o.nombre AS objetivo, h.nombre AS habito, h.frecuencia
FROM USUARIO u
JOIN OBJETIVO o ON u.id_usuario = o.id_usuario_fk
JOIN HABITO h ON o.id_objetivo = h.id_objetivo_fk
WHERE u.id_usuario = 2;

-- 5. Registros de un hábito ordenados por fecha (hábito "Leer 30 minutos", id=1)
SELECT id_registro, fecha, completado
FROM REGISTRO
WHERE id_habito_fk = 1
ORDER BY fecha ASC;

-- 6. Registros completados y no completados por hábito
SELECT h.nombre AS habito,
    COUNT(r.id_registro) AS total_registros,
    SUM(r.completado) AS completados,
    COUNT(r.id_registro) - SUM(r.completado) AS no_completados
FROM HABITO h
JOIN REGISTRO r ON h.id_habito = r.id_habito_fk
GROUP BY h.id_habito, h.nombre
ORDER BY completados DESC;

-- 7. Hábitos con sus categorías
SELECT h.nombre AS habito, c.nombre AS categoria
FROM HABITO h
JOIN HABITO_CATEGORIA hc ON h.id_habito = hc.id_habito_fk
JOIN CATEGORIA c ON hc.id_categoria_fk = c.id_categoria
ORDER BY h.nombre;

-- 8. Logros obtenidos por cada usuario con la fecha
SELECT u.nombre AS usuario, l.nombre AS logro, ul.fecha_obtenido
FROM USUARIO u
JOIN USUARIO_LOGRO ul ON u.id_usuario = ul.id_usuario_fk
JOIN LOGRO l ON ul.id_logro_fk = l.id_logro
ORDER BY u.nombre, ul.fecha_obtenido;

-- 9. Usuarios que todavía no han obtenido ningún logro
SELECT u.id_usuario, u.nombre
FROM USUARIO u
LEFT JOIN USUARIO_LOGRO ul ON u.id_usuario = ul.id_usuario_fk
WHERE ul.id_usuario_fk IS NULL;

-- 10. Objetivos activos con el número de hábitos que tienen asignados
SELECT o.nombre AS objetivo, COUNT(h.id_habito) AS num_habitos
FROM OBJETIVO o
JOIN HABITO h ON o.id_objetivo = h.id_objetivo_fk
WHERE o.estado = 'activo'
GROUP BY o.id_objetivo, o.nombre
ORDER BY num_habitos DESC;
