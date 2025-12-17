DROP DATABASE IF EXISTS tallerReparaciones;
CREATE DATABASE IF NOT EXISTS tallerReparaciones;
USE tallerReparaciones;

-- ============================================
-- TABLA CLIENTE
-- ============================================
CREATE TABLE cliente(
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(45) NOT NULL,
    dni VARCHAR(10) NOT NULL UNIQUE,
    telefono INT,
    email VARCHAR(45) NOT NULL
);

-- ============================================
-- TABLA USUARIO
-- ============================================
CREATE TABLE usuario(
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(45) NOT NULL,
    dni VARCHAR(10) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    rol ENUM('INVITADO', 'MECANICO', 'ADMINISTRADOR') NOT NULL DEFAULT 'INVITADO'
);

-- ============================================
-- TABLA VEHICULO
-- ============================================
CREATE TABLE vehiculo(
    id_vehiculo BIGINT AUTO_INCREMENT PRIMARY KEY,
    matricula VARCHAR(10) NOT NULL UNIQUE,
    marca VARCHAR(40),
    modelo VARCHAR(50),
    cliente_id BIGINT,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id_cliente)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- ============================================
-- TABLA REPARACION
-- ============================================
CREATE TABLE reparacion(
    id_reparacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(120),
    fecha_entrada DATE,
    coste_estimado DOUBLE,
    estado ENUM('PENDIENTE','EN_REPARACION', 'FINALIZADO') DEFAULT 'PENDIENTE',
    vehiculo_id BIGINT,
    usuario_id BIGINT,
    FOREIGN KEY (vehiculo_id) REFERENCES vehiculo(id_vehiculo)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id_usuario)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- ============================================
-- INSERTS DE CLIENTES
-- ============================================
INSERT INTO cliente (nombre, dni, telefono, email) VALUES
('Carlos Pérez', '11111111A', 600111222, 'carlos.perez@email.com'),
('María López', '11111112B', 600111223, 'maria.lopez@email.com'),
('José García', '11111113C', 600111224, 'jose.garcia@email.com'),
('Laura Fernández', '11111114D', 600111225, 'laura.fernandez@email.com'),
('Ana Ruiz', '11111115E', 600111226, 'ana.ruiz@email.com'),
('Javier Torres', '11111116F', 600111227, 'javier.torres@email.com'),
('Lucía Gómez', '11111117G', 600111228, 'lucia.gomez@email.com'),
('Miguel Rodríguez', '11111118H', 600111229, 'miguel.rodriguez@email.com'),
('Elena Morales', '11111119I', 600111230, 'elena.morales@email.com'),
('Pablo Díaz', '11111120J', 600111231, 'pablo.diaz@email.com');

-- ============================================
-- INSERTS DE USUARIOS
-- ============================================
INSERT INTO usuario (nombre_usuario, dni, password, rol) VALUES
('admin', '12345678A', '$2a$10$N9qo8uLOickgx2ZMRZoMye1J8Qg6ViUj3N5l5f5Z5Z5Z5Z5Z5Z5Z5', 'ADMINISTRADOR'),
('mecanico1', '12345678B', '$2a$10$N9qo8uLOickgx2ZMRZoMye1J8Qg6ViUj3N5l5f5Z5Z5Z5Z5Z5Z5Z5', 'MECANICO'),
('mecanico2', '12345678C', '$2a$10$N9qo8uLOickgx2ZMRZoMye1J8Qg6ViUj3N5l5f5Z5Z5Z5Z5Z5Z5Z5', 'MECANICO'),
('invitado', '12345678D', '$2a$10$N9qo8uLOickgx2ZMRZoMye1J8Qg6ViUj3N5l5f5Z5Z5Z5Z5Z5Z5Z5', 'INVITADO');

-- ============================================
-- INSERTS DE VEHÍCULOS
-- ============================================
INSERT INTO vehiculo (matricula, marca, modelo, cliente_id) VALUES
('1234ABC', 'Toyota', 'Corolla', 1),
('5678DEF', 'Honda', 'Civic', 1),
('1122GHI', 'Ford', 'Focus', 2),
('3344JKL', 'Seat', 'Ibiza', 2),
('5566MNO', 'BMW', 'Serie 1', 3),
('7788PQR', 'Peugeot', '308', 3),
('9900STU', 'Kia', 'Rio', 4),
('1112VWX', 'Citroen', 'C3', 4),
('1314YZA', 'Mazda', '3', 5),
('1516BCD', 'Audi', 'A3', 5);

-- ============================================
-- INSERTS DE REPARACIONES
-- ============================================
INSERT INTO reparacion (descripcion, fecha_entrada, coste_estimado, estado, vehiculo_id, usuario_id) VALUES
('Cambio de aceite', '2024-01-15', 150.00, 'FINALIZADO', 1, 2),
('Revisión de frenos', '2024-02-20', 200.00, 'FINALIZADO', 2, 2),
('Sustitución de neumáticos', '2024-03-10', 400.00, 'FINALIZADO', 3, 2),
('Reparación del motor', '2024-04-05', 1200.00, 'FINALIZADO', 4, 3),
('Cambio de batería', '2024-05-12', 180.00, 'EN_REPARACION', 5, 2),
('Revisión pre-ITV', '2024-06-18', 100.00, 'EN_REPARACION', 6, 3),
('Ajuste de suspensión', '2024-07-22', 350.00, 'PENDIENTE', 7, 2),
('Reparación de aire acondicionado', '2024-08-30', 450.00, 'PENDIENTE', 8, 2),
('Cambio de embrague', '2024-09-14', 800.00, 'PENDIENTE', 9, 3),
('Diagnóstico electrónico', '2024-10-25', 120.00, 'PENDIENTE', 10, 2);