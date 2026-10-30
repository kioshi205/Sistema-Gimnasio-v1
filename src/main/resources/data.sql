-- =========================================================================
-- 1. TABLAS SIN DEPENDENCIAS (Nivel 0)
-- =========================================================================

-- Tabla: CATEGORIA
INSERT INTO CATEGORIA (id_categoria, nombre_categoria, descripcion, estado) VALUES
                                                                                (1, 'Acceso y Cobertura', 'Permisos de ingreso, tipos de pases y sedes', 'Activo'),
                                                                                (2, 'Instalaciones y Zonas', 'Uso de áreas físicas y zonas del gimnasio', 'Activo'),
                                                                                (3, 'Comodidades Adicionales', 'Servicios de confort, conectividad e hidratación', 'Activo'),
                                                                                (4, 'Equipamiento y Tecnología', 'Dispositivos, accesorios e indumentaria', 'Activo');

-- Tabla: ROL
INSERT INTO ROL (id_rol, nombre_rol, descripcion) VALUES
                                                      (1, 'ADMINISTRADOR', 'Gestiona empleados (usuarios), planes y subproductos'),
                                                      (2, 'RECEPCIONISTA', 'Registra clientes, tramita suscripciones y gestiona planes personalizados'),
                                                      (3, 'SEGURIDAD', 'Consulta las condiciones de acceso de los clientes');

-- Tabla: SEDE
INSERT INTO SEDE (id_sede, nombre_sede, direccion, estado) VALUES
                                                               (1, 'Sede Central', 'Av. Principal 123', 'Activo'),
                                                               (2, 'Sede Norte', 'Jr. Los Olivos 456', 'Activo');

-- Tabla: CLIENTE
INSERT INTO CLIENTE (id_cliente, dni, nombre, apellido, email, telefono, fecha_registro, estado_cliente) VALUES
                                                                                                             (501, '72839401', 'Carlos', 'Mendoza Vega', 'cmendoza@gmail.com', '987654321', '2026-08-20', 'ACTIVO'),
                                                                                                             (502, '45920182', 'Ana', 'Torres Ríos', 'atorres@gmail.com', '912345678', '2026-10-05', 'ACTIVO'),
                                                                                                             (503, '10293847', 'Luis', 'Gómez Silva', 'lgomez@gmail.com', '955443322', '2026-10-10', 'ACTIVO');


-- =========================================================================
-- 2. TABLAS CON DEPENDENCIAS SIMPLES (Nivel 1)
-- =========================================================================

-- Tabla: PRODUCTO
INSERT INTO PRODUCTO (id_producto, nombre_subproducto, id_categoria, precio_individual, estado) VALUES
                                                                                                    (101, 'Ingreso y uso a la sala de gimnasio', 1, 75.00, 'Activo'),
                                                                                                    (102, 'Ingreso doble', 1, 35.00, 'Activo'),
                                                                                                    (103, 'Ingreso cuádruple', 1, 70.00, 'Activo'),
                                                                                                    (104, 'Acceso a todos los locales de la entidad', 1, 25.00, 'Activo'),
                                                                                                    (105, 'Ingreso a las duchas', 2, 15.00, 'Activo'),
                                                                                                    (106, 'Uso de casilleros', 2, 20.00, 'Activo'),
                                                                                                    (107, 'Acceso a máquinas de relajación muscular', 2, 30.00, 'Activo'),
                                                                                                    (108, 'Wifi gratis', 3, 0.00, 'Activo'),
                                                                                                    (109, 'Parking', 3, 20.00, 'Activo'),
                                                                                                    (110, 'Acceso al dispensador del agua purificada', 3, 10.00, 'Activo'),
                                                                                                    (111, 'Equipo de entrenamiento y lavado', 4, 25.00, 'Activo'),
                                                                                                    (112, 'Reloj de mano cardiomedidor', 4, 25.00, 'Activo');

-- Tabla: USUARIO
INSERT INTO USUARIO (id_usuario, id_rol, dni, nombre, apellido, username, password, estado_usuario) VALUES
                                                                                                        (201, 1, '40123456', 'Marta', 'Salazar Quispe', 'msalazar', 'admin123', 'ACTIVO'),
                                                                                                        (202, 2, '41234567', 'Jorge', 'Paredes Lazo', 'jparedes', 'recep123', 'ACTIVO'),
                                                                                                        (203, 3, '42345678', 'Rosa', 'Huamán Ccora', 'rhuaman', 'seg123', 'ACTIVO');


-- =========================================================================
-- 3. TABLAS CON MÚLTIPLES DEPENDENCIAS (Nivel 2)
-- =========================================================================

-- Tabla: PLAN
-- Nota: id_cliente es NULL para los planes predeterminados.
INSERT INTO PLAN (id_plan, nombre_plan, tipo_plan, duracion_meses, precio_total, estado, id_usuario, id_cliente) VALUES
                                                                                                                     (1, 'Plan Básico', 'PREDETERMINADO', 1, 100.00, 'Activo', 201, NULL),
                                                                                                                     (2, 'Plan Medio', 'PREDETERMINADO', 1, 170.00, 'Activo', 201, NULL),
                                                                                                                     (3, 'Plan Pro', 'PREDETERMINADO', 1, 280.00, 'Activo', 201, NULL),
                                                                                                                     (4, 'Plan Personalizado Gómez', 'PERSONALIZADO', 1, 110.00, 'Activo', 202, 503);


-- =========================================================================
-- 4. TABLAS DE DETALLE Y TRANSACCIONALES (Nivel 3)
-- =========================================================================

-- Tabla: PLAN_DETALLE
INSERT INTO PLAN_DETALLE (id_plan_detalle, id_plan, id_producto, estado) VALUES
                                                                             (1, 1, 101, 'Activo'),
                                                                             (2, 1, 105, 'Activo'),
                                                                             (3, 1, 108, 'Activo'),
                                                                             (4, 1, 110, 'Activo'),
                                                                             (5, 2, 101, 'Activo'),
                                                                             (6, 2, 105, 'Activo'),
                                                                             (7, 2, 108, 'Activo'),
                                                                             (8, 2, 110, 'Activo'),
                                                                             (9, 2, 106, 'Activo'),
                                                                             (10, 2, 107, 'Activo'),
                                                                             (11, 2, 109, 'Activo'),
                                                                             (12, 3, 101, 'Activo'),
                                                                             (13, 3, 105, 'Activo'),
                                                                             (14, 3, 108, 'Activo'),
                                                                             (15, 3, 110, 'Activo'),
                                                                             (16, 3, 106, 'Activo'),
                                                                             (17, 3, 107, 'Activo'),
                                                                             (18, 3, 109, 'Activo'),
                                                                             (19, 3, 104, 'Activo'),
                                                                             (20, 3, 102, 'Activo'),
                                                                             (21, 3, 111, 'Activo'),
                                                                             (22, 3, 112, 'Activo'),
                                                                             (23, 4, 101, 'Activo'),
                                                                             (24, 4, 105, 'Activo'),
                                                                             (25, 4, 109, 'Activo');

-- Tabla: SUSCRIPCION
INSERT INTO SUSCRIPCION (id_suscripcion, id_cliente, id_plan, id_usuario, id_sede, fecha_inicio, fecha_fin, monto_pagado, estado_suscripcion) VALUES
                                                                                                                                                  (1001, 501, 2, 202, 1, '2026-09-01', '2026-10-01', 170.00, 'ACTIVO'),
                                                                                                                                                  (1002, 502, 1, 202, 2, '2026-10-05', '2026-11-05', 100.00, 'ACTIVO'),
                                                                                                                                                  (1003, 503, 4, 202, 1, '2026-10-10', '2026-11-10', 110.00, 'ACTIVO');

-- Tabla: MARCACION_ACCESO
-- Nota: id_cliente e id_suscripcion son NULL para los accesos denegados por DNI no registrado.
INSERT INTO MARCACION_ACCESO (id_marcacion, id_cliente, id_suscripcion, id_usuario, id_sede, dni_consultado, fecha_hora, resultado, motivo) VALUES
                                                                                                                                                (9001, 502, 1002, 203, 2, '45920182', '2026-10-12 08:30:12', 'PERMITIDO', 'Suscripción vigente en su sede'),
                                                                                                                                                (9002, 503, 1003, 203, 1, '10293847', '2026-10-12 09:05:40', 'PERMITIDO', 'Suscripción vigente en su sede'),
                                                                                                                                                (9003, 501, 1001, 203, 1, '72839401', '2026-10-12 18:20:03', 'DENEGADO', 'Suscripción vencida'),
                                                                                                                                                (9004, NULL, NULL, 203, 1, '99887766', '2026-10-12 18:25:31', 'DENEGADO', 'DNI no registrado');

-- Las marcaciones nuevas (I15) deben continuar después de los registros de ejemplo.
ALTER TABLE MARCACION_ACCESO ALTER COLUMN id_marcacion RESTART WITH 9005;