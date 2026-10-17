CREATE TABLE cliente (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         dni VARCHAR(15) NOT NULL
);

CREATE TABLE plan (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      nombre VARCHAR(50) NOT NULL,
                      precio DECIMAL(10,2) NOT NULL,
                      duracion_dias INT NOT NULL
);

CREATE TABLE subproducto (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             nombre VARCHAR(50) NOT NULL,
                             precio DECIMAL(10,2) NOT NULL
);

CREATE TABLE inscripcion (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             cliente_id INT NOT NULL,
                             plan_id INT NOT NULL,
                             total DECIMAL(10,2) NOT NULL,
                             fecha_inicio DATE NOT NULL,
                             fecha_fin DATE NOT NULL,
                             FOREIGN KEY (cliente_id) REFERENCES cliente(id),
                             FOREIGN KEY (plan_id) REFERENCES plan(id)
);

CREATE TABLE inscripcion_subproducto (
                                         inscripcion_id INT NOT NULL,
                                         subproducto_id INT NOT NULL,
                                         PRIMARY KEY (inscripcion_id, subproducto_id),
                                         FOREIGN KEY (inscripcion_id) REFERENCES inscripcion(id),
                                         FOREIGN KEY (subproducto_id) REFERENCES subproducto(id)
);