CREATE TABLE IF NOT EXISTS socio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100),
    dni VARCHAR(20),
    fecha_vencimiento DATE
    );
