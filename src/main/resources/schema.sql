-- =========================================================================
-- 0. LIMPIEZA DE TABLAS (Orden inverso a la creación para evitar errores FK)
-- =========================================================================
DROP TABLE IF EXISTS MARCACION_ACCESO;
DROP TABLE IF EXISTS SUSCRIPCION;
DROP TABLE IF EXISTS PLAN_DETALLE;
DROP TABLE IF EXISTS PLAN;
DROP TABLE IF EXISTS USUARIO;
DROP TABLE IF EXISTS PRODUCTO;
DROP TABLE IF EXISTS CLIENTE;
DROP TABLE IF EXISTS SEDE;
DROP TABLE IF EXISTS ROL;
DROP TABLE IF EXISTS CATEGORIA;

-- =========================================================================
-- 1. TABLAS SIN DEPENDENCIAS (Nivel 0)
-- =========================================================================
CREATE TABLE CATEGORIA (
                           id_categoria INT AUTO_INCREMENT PRIMARY KEY,
                           nombre_categoria VARCHAR(100) NOT NULL,
                           descripcion VARCHAR(255),
                           estado VARCHAR(20) NOT NULL
);

CREATE TABLE ROL (
                     id_rol INT AUTO_INCREMENT PRIMARY KEY,
                     nombre_rol VARCHAR(50) NOT NULL,
                     descripcion VARCHAR(255)
);

CREATE TABLE SEDE (
                      id_sede INT AUTO_INCREMENT PRIMARY KEY,
                      nombre_sede VARCHAR(100) NOT NULL,
                      direccion VARCHAR(255) NOT NULL,
                      estado VARCHAR(20) NOT NULL
);

CREATE TABLE CLIENTE (
                         id_cliente INT AUTO_INCREMENT PRIMARY KEY,
                         dni VARCHAR(20) UNIQUE NOT NULL,
                         nombre VARCHAR(100) NOT NULL,
                         apellido VARCHAR(100) NOT NULL,
                         email VARCHAR(100),
                         telefono VARCHAR(20),
                         fecha_registro DATE NOT NULL,
                         estado_cliente VARCHAR(20) NOT NULL
);

-- =========================================================================
-- 2. TABLAS CON DEPENDENCIAS SIMPLES (Nivel 1)
-- =========================================================================
CREATE TABLE PRODUCTO (
                          id_producto INT AUTO_INCREMENT PRIMARY KEY,
                          nombre_subproducto VARCHAR(100) NOT NULL,
                          id_categoria INT NOT NULL,
                          precio_individual DECIMAL(10,2) NOT NULL,
                          estado VARCHAR(20) NOT NULL,
                          FOREIGN KEY (id_categoria) REFERENCES CATEGORIA(id_categoria)
);

CREATE TABLE USUARIO (
                         id_usuario INT AUTO_INCREMENT PRIMARY KEY,
                         id_rol INT NOT NULL,
                         dni VARCHAR(20) UNIQUE NOT NULL,
                         nombre VARCHAR(100) NOT NULL,
                         apellido VARCHAR(100) NOT NULL,
                         username VARCHAR(50) UNIQUE NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         estado_usuario VARCHAR(20) NOT NULL,
                         FOREIGN KEY (id_rol) REFERENCES ROL(id_rol)
);

-- =========================================================================
-- 3. TABLAS CON MÚLTIPLES DEPENDENCIAS (Nivel 2)
-- =========================================================================
CREATE TABLE PLAN (
                      id_plan INT AUTO_INCREMENT PRIMARY KEY,
                      nombre_plan VARCHAR(100) NOT NULL,
                      tipo_plan VARCHAR(50) NOT NULL,
                      duracion_meses INT NOT NULL,
                      precio_total DECIMAL(10,2) NOT NULL,
                      estado VARCHAR(20) NOT NULL,
                      id_usuario INT NOT NULL, -- Creador del plan
                      id_cliente INT,          -- Opcional, para planes personalizados
                      FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario),
                      FOREIGN KEY (id_cliente) REFERENCES CLIENTE(id_cliente)
);

-- =========================================================================
-- 4. TABLAS DE DETALLE Y TRANSACCIONALES (Nivel 3)
-- =========================================================================
CREATE TABLE PLAN_DETALLE (
                              id_plan_detalle INT AUTO_INCREMENT PRIMARY KEY,
                              id_plan INT NOT NULL,
                              id_producto INT NOT NULL,
                              estado VARCHAR(20) NOT NULL,
                              FOREIGN KEY (id_plan) REFERENCES PLAN(id_plan),
                              FOREIGN KEY (id_producto) REFERENCES PRODUCTO(id_producto)
);

CREATE TABLE SUSCRIPCION (
                             id_suscripcion INT AUTO_INCREMENT PRIMARY KEY,
                             id_cliente INT NOT NULL,
                             id_plan INT NOT NULL,
                             id_usuario INT NOT NULL, -- Recepcionista que registró
                             id_sede INT NOT NULL,    -- Sede base
                             fecha_inicio DATE NOT NULL,
                             fecha_fin DATE NOT NULL,
                             monto_pagado DECIMAL(10,2) NOT NULL,
                             estado_suscripcion VARCHAR(20) NOT NULL,
                             FOREIGN KEY (id_cliente) REFERENCES CLIENTE(id_cliente),
                             FOREIGN KEY (id_plan) REFERENCES PLAN(id_plan),
                             FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario),
                             FOREIGN KEY (id_sede) REFERENCES SEDE(id_sede)
);

CREATE TABLE MARCACION_ACCESO (
                                  id_marcacion INT AUTO_INCREMENT PRIMARY KEY,
                                  id_cliente INT,          -- Opcional
                                  id_suscripcion INT,      -- Opcional
                                  id_usuario INT NOT NULL, -- Personal de seguridad
                                  id_sede INT NOT NULL,
                                  dni_consultado VARCHAR(20) NOT NULL,
                                  fecha_hora TIMESTAMP NOT NULL,
                                  resultado VARCHAR(50) NOT NULL,
                                  motivo VARCHAR(255),
                                  FOREIGN KEY (id_cliente) REFERENCES CLIENTE(id_cliente),
                                  FOREIGN KEY (id_suscripcion) REFERENCES SUSCRIPCION(id_suscripcion),
                                  FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario),
                                  FOREIGN KEY (id_sede) REFERENCES SEDE(id_sede)
);