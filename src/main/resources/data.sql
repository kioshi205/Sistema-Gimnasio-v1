INSERT INTO cliente (nombre, dni) VALUES ('Ana Torres', '70111111');
INSERT INTO cliente (nombre, dni) VALUES ('Luis Rojas', '70222222');
INSERT INTO cliente (nombre, dni) VALUES ('María Quispe', '70333333');
INSERT INTO cliente (nombre, dni) VALUES ('Carlos Ramos', '70444444');

INSERT INTO plan (nombre, precio, duracion_dias) VALUES ('Mensual', 80.00, 30);
INSERT INTO plan (nombre, precio, duracion_dias) VALUES ('Trimestral', 210.00, 90);
INSERT INTO plan (nombre, precio, duracion_dias) VALUES ('Anual', 750.00, 365);

INSERT INTO subproducto (nombre, precio) VALUES ('Casillero', 20.00);
INSERT INTO subproducto (nombre, precio) VALUES ('Entrenador personal', 60.00);
INSERT INTO subproducto (nombre, precio) VALUES ('Clases grupales', 40.00);

-- Inscripciones de ejemplo: activa, por vencer (3 días) y vencida
INSERT INTO inscripcion (cliente_id, plan_id, total, fecha_inicio, fecha_fin)
VALUES (1, 1, 100.00, DATEADD('DAY', -10, CURRENT_DATE), DATEADD('DAY', 20, CURRENT_DATE));
INSERT INTO inscripcion (cliente_id, plan_id, total, fecha_inicio, fecha_fin)
VALUES (2, 1, 140.00, DATEADD('DAY', -27, CURRENT_DATE), DATEADD('DAY', 3, CURRENT_DATE));
INSERT INTO inscripcion (cliente_id, plan_id, total, fecha_inicio, fecha_fin)
VALUES (3, 1, 80.00, DATEADD('DAY', -34, CURRENT_DATE), DATEADD('DAY', -4, CURRENT_DATE));

INSERT INTO inscripcion_subproducto VALUES (1, 1);
INSERT INTO inscripcion_subproducto VALUES (2, 2);