-- ELIMINAR todas las tablas antes de crear
DROP TABLE IF EXISTS detalle_opciones_seleccionadas CASCADE;
DROP TABLE IF EXISTS detalles_pedido CASCADE;
DROP TABLE IF EXISTS pedidos CASCADE;
DROP TABLE IF EXISTS favoritos CASCADE;
DROP TABLE IF EXISTS tarjetas_usuario CASCADE;
DROP TABLE IF EXISTS cupones CASCADE;
DROP TABLE IF EXISTS producto_grupos CASCADE;
DROP TABLE IF EXISTS producto_escalas CASCADE;
DROP TABLE IF EXISTS producto CASCADE;
DROP TABLE IF EXISTS opciones_personalizacion CASCADE;
DROP TABLE IF EXISTS grupo_personalizacion CASCADE;
DROP TABLE IF EXISTS escala CASCADE;
DROP TABLE IF EXISTS categorias CASCADE;
DROP TABLE IF EXISTS cafeterias CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;
DROP TABLE IF EXISTS tokens_recuperacion CASCADE;
DROP TYPE IF EXISTS rol_usuario CASCADE;
DROP TYPE IF EXISTS estado_pedido CASCADE;
DROP TYPE IF EXISTS metodo_pago CASCADE;
DROP TYPE IF EXISTS tipo_entrega CASCADE;
DROP TYPE IF EXISTS proveedor_auth CASCADE;


-- Crear los tipos ENUM
-- Roles de usuario
CREATE TYPE rol_usuario AS ENUM ('CLIENTE',  'ADMIN', 'BARISTA' );

-- Proveedor de autentificacion
CREATE TYPE proveedor_auth AS ENUM ('LOCAL', 'GOOGLE', 'FACEBOOK', 'APPLE' );

-- Tipos de entrega
CREATE TYPE tipo_entrega AS ENUM ('RECOGIDA_PROGRAMADA','RECOGIDA_INMEDIATA','ENTREGA');

-- Metodos de pago
CREATE TYPE metodo_pago AS ENUM ('CARD', 'CASH', 'PAYPAL');

-- Estado del pedido
CREATE TYPE estado_pedido AS ENUM ('PEDIDO_REALIZADO', 'PREPARANDO', 'LISTO', 'COMPLETADO', 'CANCELADO');

-- Tabla de usuarios
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    fullName VARCHAR(120) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NULL,
    rol rol_usuario DEFAULT 'CLIENTE',
    proveedor proveedor_auth DEFAULT 'LOCAL',
    puntosRecompensa INT DEFAULT 0,
    activo BOOLEAN DEFAULT TRUE,
    created_At TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_At TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_usuarios_email ON usuarios(email);

-- Tabla de las tarjetas de usuario
CREATE TABLE tarjetas_usuario (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    marca VARCHAR(30) NOT NULL,                    -- 'MasterCard', 'Visa'
    ultimos_cuatro CHAR(4),               -- '2048' (para mostrar en UI)
    numero_encriptado VARCHAR(255),       -- Cifrado con AES-256-GCM + IV en Base64
    titular VARCHAR(120) NOT NULL,
    predeterminado BOOLEAN DEFAULT FALSE,
    created_At TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- TABLA de cafeterias
CREATE TABLE cafeterias (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    ciudad VARCHAR(80) NOT NULL,
    latitud NUMERIC(10, 7),
    longitud NUMERIC(10, 7),
    hora_apertura TIME NOT NULL,
    hora_cierre TIME NOT NULL,
    frecuente BOOLEAN DEFAULT FALSE,
    activo BOOLEAN DEFAULT TRUE,
    created_At TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Index para buscar más rapido la cafeteria
CREATE INDEX idx_cafeterias_ciudad ON cafeterias(ciudad);

-- Tabla de las categorias
CREATE TABLE categorias (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(80) UNIQUE,
    icono_url VARCHAR(255),
    orden_visual INT DEFAULT 0,
    activa BOOLEAN DEFAULT TRUE
);

-- Tabla de la escala de tamaño
CREATE TABLE escala (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,              -- 'pequeño', 'mediano', 'grande'
    volumen_ml INT NOT NULL,                  -- 250, 350, 450
    recargo_precio NUMERIC(10, 2) DEFAULT 0.00
);

-- Tabla del grupo de personalizacion
CREATE TABLE grupo_personalizacion (
    id SERIAL PRIMARY KEY,
    nombre_grupo VARCHAR(80) NOT NULL,        -- 'Milk', 'Whipped Cream', 'Caffeine'
    seleccion_multiple BOOLEAN DEFAULT FALSE,
    obligatorio BOOLEAN DEFAULT FALSE
);

-- Tabla de las opciones de personalizacion
CREATE TABLE opciones_personalizacion (
    id SERIAL PRIMARY KEY,
    grupo_id INT NOT NULL REFERENCES grupo_personalizacion(id) ON DELETE CASCADE,
    nombre VARCHAR(100) NOT NULL,             -- 'Full-fat milk', 'Oat milk (+$0.7)'
    recargo_precio NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    por_defecto BOOLEAN NOT NULL DEFAULT FALSE
);

-- Tabla de productos
CREATE TABLE producto (
    id SERIAL PRIMARY KEY,
    categoria_id INT NOT NULL REFERENCES categorias(id) ON DELETE RESTRICT,
    nombre VARCHAR(120) NOT NULL,
    descripcion TEXT,
    imagen_url VARCHAR(255),
    precio_base NUMERIC(10, 2) NOT NULL,
    nuevo BOOLEAN DEFAULT FALSE,
    frecuente BOOLEAN DEFAULT FALSE,
    activo BOOLEAN DEFAULT TRUE,
    created_At TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_At TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Index para buscar más rápido los productos por categoria
CREATE INDEX idx_producto_categoria ON producto(categoria_id);

-- Tabla de la relación entre productos y escalas
CREATE TABLE producto_escalas (
    producto_id INT NOT NULL REFERENCES producto(id) ON DELETE CASCADE,
    escala_id INT NOT NULL REFERENCES escala(id) ON DELETE CASCADE,
    PRIMARY KEY (producto_id, escala_id)
);

-- Tabla de la relacion entre productos y grupos
CREATE TABLE producto_grupos (
    producto_id INT NOT NULL REFERENCES producto(id) ON DELETE CASCADE,
    grupo_id INT NOT NULL REFERENCES grupo_personalizacion(id) ON DELETE CASCADE,
    PRIMARY KEY (producto_id, grupo_id)
);

-- Tabla de favoritos
CREATE TABLE favoritos (
    usuario_id INT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    producto_id INT NOT NULL REFERENCES producto(id) ON DELETE CASCADE,
    created_At TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (usuario_id, producto_id)
);

-- Tabla de cupones
CREATE TABLE cupones (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    porcentaje_descuento NUMERIC(5, 2) DEFAULT 0.00,
    monto_descuento_fijo NUMERIC(10, 2) DEFAULT 0.00,
    fecha_expiracion TIMESTAMPTZ,
    activo BOOLEAN DEFAULT TRUE
);

-- Tabla de pedidos
CREATE TABLE pedidos (
     id SERIAL PRIMARY KEY,
     usuario_id INT NOT NULL REFERENCES usuarios(id) ON DELETE RESTRICT,
     cafeteria_id INT NOT NULL REFERENCES cafeterias(id) ON DELETE RESTRICT,
     codigo_orden VARCHAR(20) NOT NULL,
     tipo_entrega tipo_entrega NOT NULL,
     fecha_recojo DATE,
     hora_recojo TIME,
     metodo_pago metodo_pago NOT NULL,
     referencia_pago VARCHAR(100),                -- 'MasterCard **** 2048'
     estado estado_pedido DEFAULT 'PEDIDO_REALIZADO',
     subtotal NUMERIC(10, 2) NOT NULL,
     descuento NUMERIC(10, 2) DEFAULT 0.00,
     total NUMERIC(10, 2) NOT NULL,
     created_At TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Tabla de detalles del pedido
CREATE TABLE detalles_pedido (
    id SERIAL PRIMARY KEY,
    pedido_id INT NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
    producto_id INT NOT NULL REFERENCES producto(id) ON DELETE RESTRICT,
    nombre_producto VARCHAR(120) NOT NULL,
    nombre_escala VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario NUMERIC(10, 2) NOT NULL,
    subtotal_item NUMERIC(10, 2) NOT NULL
);

-- Tabla relacional entre detalles del pedido y las opciones de personalizacion seleccionadas
CREATE TABLE detalle_opciones_seleccionadas (
    id SERIAL PRIMARY KEY,
    detalle_pedido_id INT NOT NULL REFERENCES detalles_pedido(id) ON DELETE CASCADE,
    nombre_grupo VARCHAR(80) NOT NULL,
    nombre_opcion VARCHAR(100) NOT NULL,
    recargo NUMERIC(10, 2) DEFAULT 0.00
);

-- Índice para la busqueda de opciones seleccionadas
CREATE INDEX IF NOT EXISTS idx_detalle_opciones_seleccionadas_detalle_pedido ON detalle_opciones_seleccionadas(detalle_pedido_id);

-- Tabla para los tokens de recuperacion de contraseñas
CREATE TABLE tokens_recuperacion (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    token VARCHAR(100) NOT NULL UNIQUE,
    expiracion TIMESTAMPTZ NOT NULL,
    usado BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Índice para la busqueda de tokens
CREATE INDEX IF NOT EXISTS idx_tokens_recuperacion_token ON tokens_recuperacion(token);