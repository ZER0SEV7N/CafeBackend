-- Sede y cafeteria
INSERT INTO cafeterias (id, nombre, direccion, ciudad, latitud, longitud, hora_apertura, hora_cierre, activo)
VALUES
    (1, 'Cavosh Cafe', 'Legnicka 20, Wroclaw', 'Wroclaw', 51.1162000, 17.0089000, '08:00:00', '22:00:00',  TRUE),
    (2, 'Cavosh Cafe', 'Legnicka 5, Wroclaw', 'Wroclaw', 51.1147000, 17.0145000, '08:00:00', '22:00:00',  TRUE),
    (3, 'Cavosh Cafe', 'Rynek 12, Wroclaw', 'Wroclaw', 51.1095000, 17.0315000, '07:30:00', '23:00:00',  TRUE)
ON CONFLICT (id) DO NOTHING;

-- Categorias
INSERT INTO categorias (id, nombre, icono_url, orden_visual, activa)
VALUES
    (1, 'Bebida caliente', 'https://cdn-icons-png.flaticon.com/512/924/924514.png', 1, TRUE),
    (2, 'Bebida fria', 'https://cdn-icons-png.flaticon.com/512/2935/2935413.png', 2, TRUE),
    (3, 'Panaderia', 'https://cdn-icons-png.flaticon.com/512/3014/3014521.png', 3, TRUE)
ON CONFLICT (id) DO NOTHING;

-- Escala de tamaños
INSERT INTO escala (id, nombre, volumen_ml, recargo_precio)
VALUES
    (1, 'Pequeño', 250, 0.00),
    (2, 'Mediano', 350, 0.50),
    (3, 'Grande', 450, 1.00),
    (4, 'Extra Grande', 600, 2)
ON CONFLICT (id) DO NOTHING;

-- Grupo de personalizacion
INSERT INTO grupo_personalizacion (id, nombre_grupo, seleccion_multiple, obligatorio)
VALUES
    (1, 'Leche', FALSE, TRUE),
    (2, 'Crema Batida', FALSE, FALSE),
    (3, 'Cafeina', FALSE, FALSE)
ON CONFLICT (id) DO NOTHING;

-- Opciones de personalizacion
INSERT INTO opciones_personalizacion (id, grupo_id, nombre, recargo_precio, por_defecto)
VALUES
-- Opciones de Leche (Grupo 1)
(1, 1, 'Leche entera', 0.00, TRUE),
(2, 1, 'Leche deslactosada', 0.00, FALSE),
(3, 1, 'Leche de soya', 0.00, FALSE),
(4, 1, 'Leche descremada', 0.00, FALSE),
(5, 1, 'Leche de almendras', 0.70, FALSE),
(6, 1, 'Leche de avena', 0.70, FALSE),

-- Opciones de Crema Batida (Grupo 2)
(7, 2, 'Sin crema batida', 0.00, TRUE),
(8, 2, 'Con crema batida', 0.50, FALSE),

-- Opciones de Cafeína (Grupo 3)
(9, 3, 'Con cafeina', 0.00, TRUE),
(10, 3, 'Sin cafeina', 0.00, FALSE)
ON CONFLICT (id) DO NOTHING;

-- Productos
INSERT INTO producto (id, categoria_id, nombre, descripcion, imagen_url, precio_base, nuevo, activo)
VALUES
    (1, 1, 'Caramel Macchiato', 'Nuestro Caramel Macchiato es la combinación perfecta de un espresso intenso, leche cremosa y el dulce aroma a caramelo.', 'https://images.unsplash.com/photo-1541167760496-1628856ab772?q=80&w=600&auto=format&fit=crop', 4.00, TRUE,  TRUE),
    (2, 1, 'Latte de Vainilla', 'Un espresso intenso equilibrado con leche vaporizada y un sutil toque dulce de vainilla.', 'https://images.unsplash.com/photo-1572442388796-11668a67e53d?q=80&w=600&auto=format&fit=crop', 3.00, TRUE,  TRUE),
    (3, 1, 'Capuchino Tradicional', 'Espresso oscuro e intenso cubierto con una suave y densa capa de espuma de leche.', 'https://images.unsplash.com/photo-1534778101976-62847782c213?q=80&w=600&auto=format&fit=crop', 3.00, FALSE,  TRUE),
    (4, 1, 'Moca de Chocolate Blanco', 'Espresso, leche vaporizada y deliciosa salsa de chocolate blanco terminado con crema batida.', 'https://images.unsplash.com/photo-1570968915860-54d5c301fa9f?q=80&w=600&auto=format&fit=crop', 4.00, TRUE, TRUE),
    (5, 1, 'Café Moca', 'Reconfortante moca que combina espresso, cacao semiamargo y leche vaporizada.', 'https://images.unsplash.com/photo-1578314675249-a6910f80cc4e?q=80&w=600&auto=format&fit=crop', 3.80, FALSE, TRUE),
    (6, 3, 'Rol de Canela', 'Espiral recién horneado con canela dulce y un cremoso glaseado de vainilla.', 'https://images.unsplash.com/photo-1509365465651-6425a27877be?q=80&w=600&auto=format&fit=crop', 3.50, FALSE, TRUE)
ON CONFLICT (id) DO NOTHING;

-- Tablas intermediarias: PRODUCTO <-> ESCALA
INSERT INTO producto_escalas (producto_id, escala_id)
VALUES
    (1, 1), (1, 2), (1, 3),
    (2, 1), (2, 2), (2, 3),
    (3, 1), (3, 2), (3, 3),
    (4, 1), (4, 2), (4, 3),
    (5, 1), (5, 2), (5, 3)
ON CONFLICT (producto_id, escala_id) DO NOTHING;

-- Tablas intermediarias: PRODUCTO <-> GRUPO PERSONALIZACION
INSERT INTO producto_grupos (producto_id, grupo_id)
VALUES
    (1, 1), (1, 2), (1, 3),
    (2, 1), (2, 2), (2, 3),
    (3, 1), (3, 2), (3, 3),
    (4, 1), (4, 2), (4, 3),
    (5, 1), (5, 2), (5, 3)
ON CONFLICT (producto_id, grupo_id) DO NOTHING;

-- Cupones de descuento
INSERT INTO cupones (id, codigo, porcentaje_descuento, monto_descuento_fijo, fecha_expiracion, activo)
VALUES
    (1, 'CAVOSH10', 0.00, 1.20, '2030-12-31 23:59:59+00', TRUE),
    (2, 'WELCOME15', 15.00, 0.00, '2030-12-31 23:59:59+00', TRUE)
ON CONFLICT (id) DO NOTHING;

-- Sincronizar secuencias de ID
SELECT setval('cafeterias_id_seq', (SELECT COALESCE(MAX(id), 1) FROM cafeterias));
SELECT setval('categorias_id_seq', (SELECT COALESCE(MAX(id), 1) FROM categorias));
SELECT setval('escala_id_seq', (SELECT COALESCE(MAX(id), 1) FROM escala));
SELECT setval('grupo_personalizacion_id_seq', (SELECT COALESCE(MAX(id), 1) FROM grupo_personalizacion));
SELECT setval('opciones_personalizacion_id_seq', (SELECT COALESCE(MAX(id), 1) FROM opciones_personalizacion));
SELECT setval('producto_id_seq', (SELECT COALESCE(MAX(id), 1) FROM producto));
SELECT setval('cupones_id_seq', (SELECT COALESCE(MAX(id), 1) FROM cupones));