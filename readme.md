# Cavosh - Coffee Shop Backend API

Backend RESTful de alta disponibilidad para la gestión comercial y operativa de cafeterías **Cavosh**, desarrollado con **Spring Boot 3**, **PostgreSQL 16** y una arquitectura desacoplada orientada al dominio.

---

## Arquitectura del Sistema

El proyecto implementa los principios de **Arquitectura Hexagonal (Ports & Adapters)** combinados con conceptos tácticos de **Domain-Driven Design (DDD)**. La separación en capas aísla las reglas de negocio de los detalles tecnológicos externos (bases de datos, controladores HTTP, proveedores de seguridad y librerías de terceros).

```text
src/main/java/com/cavosh/cafebackend/
├── [modulo]/
│   ├── domain/                         # Núcleo del Dominio (Puro Java, agnóstico al framework)
│   │   ├── model/                      # Entidades del dominio, Records y Value Objects
│   │   └── ports/
│   │       ├── in/                     # Puertos de entrada (Casos de uso consumidos por la API)
│   │       └── out/                    # Puertos de salida (Contratos para persistencia y servicios)
│   ├── application/                    # Capa de Aplicación
│   │   └── usecases/                   # Implementaciones de lógica de negocio y transacciones
│   └── infrastructure/                 # Capa de Infraestructura (Adaptadores)
│       └── adapter/
│           ├── in/                     # Adaptadores de entrada (Controladores REST, DTOs, OpenAPI)
│           └── out/                    # Adaptadores de salida (JPA Repositories, Entities, Cifrado, DB)
└── global/                             # Infraestructura transversal (Manejo de excepciones, JWT, Utils)
```

### Principios de Diseño
* **Independencia del Framework en el Dominio:** Los modelos en `domain.model` no dependen de anotaciones JPA ni validadores de Jakarta; operan como contratos puros.
* **Inversión de Dependencias (DIP):** Los casos de uso (`application`) interactúan únicamente contra interfaces (`ports.out`). Los repositorios de infraestructura implementan estos puertos.
* **Seguridad Criptográfica AES-256-GCM:** Para el cumplimiento de normativas de datos de pago, la información sensible de tarjetas nunca se guarda en texto plano ni con algoritmos obsoletos; se emplea **AES-256-GCM** autenticado con vector de inicialización dinámico (IV de 12 bytes) y Authentication Tag de 128 bits.

---

## Stack Tecnológico

| Componente | Tecnología | Detalle |
| :--- | :--- | :--- |
| **Lenguaje** | Java 17 / 21 | Sintaxis moderna (Records, Pattern Matching) |
| **Framework** | Spring Boot 3 / Spring Framework 6 | Spring Web, Spring Security, Spring Data JPA |
| **Base de Datos** | PostgreSQL 16 (Alpine) | Motor relacional con Stored Procedures para optimización |
| **Criptografía** | AES-256-GCM & JJWT | Cifrado de datos sensibles en reposo y tokens Bearer |
| **Contenedores** | Docker & Docker Compose | Inicialización de infraestructura reproducible |
| **Construcción** | Gradle | Gestor de dependencias y empaquetado |

---

## Despliegue Local con Docker Compose

El proyecto automatiza la creación del esquema y la carga inicial de datos mediante scripts montados en `/docker-entrypoint-initdb.d/`.

### 1. Estructura de Inicialización de BD

Verifica que tu carpeta `init-db/` en la raíz del proyecto contenga los archivos SQL correspondientes:
* `init-db/basedatos.sql`: Estructura de tablas, índices y funciones almacenadas (`sp_obtener_tiendas_frecuentes`, `sp_obtener_productos_frecuentes`).
* `init-db/datos.sql`: Inserciones de categorías, cafeterías y roles iniciales.

### 2. Levantar la Base de Datos

Ejecuta en la terminal de la raíz del proyecto:

```bash
docker compose up -d postgres
```

Para verificar que el contenedor esté corriendo y la inicialización haya sido limpia:

```bash
docker ps
docker logs cavosh_postgres
```

> **Reinicio Limpio:** Si necesitas reconstruir la base de datos desde cero eliminando volúmenes residuales:
> ```bash
> docker compose down -v
> docker compose up -d postgres
> ```

### 3. Ejecución del Backend

Compila y levanta la aplicación asegurando el flag de parámetros para Spring Boot 3:

```bash
./gradlew bootRun
```

La aplicación arrancará por defecto en el puerto `8080` conectándose a `jdbc:postgresql://localhost:5432/cavoshDB`.

---

## Catálogo de Endpoints de la API

Todas las respuestas exitosas y de error siguen el estándar envoltorio unificado `ResponseGlobal<T>`:

```json
{
  "success": true,
  "status": 200,
  "message": "Mensaje descriptivo",
  "data": { },
  "timestamp": "2026-09-10T22:00:00Z"
}
```

### 1. Autenticación (`/api/auth`)

| Método | Endpoint | Acceso | Descripción |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Público | Registro de nuevos clientes. |
| `POST` | `/api/auth/login` | Público | Autenticación y retorno de token JWT Bearer. |

---

### 2. Productos y Catálogo (`/api/productos`)

| Método | Endpoint | Acceso | Descripción |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/productos` | Público | Listado de productos activos con sus categorías. |
| `GET` | `/api/productos/{id}` | Público | Detalle completo de un producto (escalas y grupos de personalización). |
| `GET` | `/api/productos/frecuentes` | Autenticado | Obtiene productos recurrentes calculados por procedimiento almacenado. |
| `POST` | `/api/productos` | `ADMIN` | Registra un nuevo producto vinculando categorías y escalas. |
| `PUT` | `/api/productos/{id}` | `ADMIN` | Actualiza un producto existente en su totalidad. |
| `PATCH` | `/api/productos/{id}/estado?activo={bool}` | `ADMIN` | Activa o desactiva lógicamente un producto del menú. |

---

### 3. Personalización y Categorías

| Método | Endpoint | Acceso | Descripción |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/categorias` | Público | Obtiene la lista de categorías visibles. |
| `POST` | `/api/grupos-personalizacion` | `ADMIN` | Crea un grupo de opciones (ej. tipo de leche, endulzantes, jarabes). |

---

### 4. Sedes y Cafeterías (`/api/tiendas`)

| Método | Endpoint | Acceso | Descripción |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/tiendas` | Público | Lista todas las sucursales activas, coordenadas y horarios. |
| `GET` | `/api/tiendas/buscar/{ciudad}` | Público | Filtra cafeterías por ubicación geográfica/ciudad. |
| `GET` | `/api/tiendas/frecuentes` | Autenticado | Retorna las tiendas más visitadas por el usuario autenticado (últimos 30 días). |

---

### 5. Métodos de Pago (`/api/tarjetas`)

Todos los endpoints requieren header de autorización `Bearer {{token}}`. Los números de tarjeta son procesados con cifrado en reposo.

| Método | Endpoint | Acceso | Descripción |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/tarjetas` | Autenticado | Lista las tarjetas guardadas del usuario con máscara (ej. `**** 2048`). |
| `POST` | `/api/tarjetas` | Autenticado | Cifra y guarda un nuevo método de pago (retorna ID y últimos 4 dígitos). |
| `PATCH` | `/api/tarjetas/{id}/predeterminada` | Autenticado | Define una tarjeta como predeterminada y desmarca las demás. |
| `DELETE` | `/api/tarjetas/{id}` | Autenticado | Remueve un método de pago de la cuenta del usuario. |

---

### 6. Productos Favoritos (`/api/favoritos`)

| Método | Endpoint | Acceso | Descripción |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/favoritos` | Autenticado | Lista los productos guardados como preferidos por el usuario. |
| `GET` | `/api/favoritos/{productoId}/check` | Autenticado | Verifica si un producto específico ya forma parte de sus favoritos (`true`/`false`). |
| `POST` | `/api/favoritos/{productoId}` | Autenticado | Añade un producto a la lista de favoritos. |
| `DELETE` | `/api/favoritos/{productoId}` | Autenticado | Elimina un producto de la lista de favoritos. |

---

## Variables de Entorno Principales

Configuradas en `src/main/resources/application.properties` o inyectadas vía variables de entorno:

```properties
# Base de Datos
spring.datasource.url=jdbc:postgresql://localhost:5432/cavoshDB
spring.datasource.username=cavosh
spring.datasource.password=cavosh123

# Criptografía y Seguridad
app.security.jwt-secret=tu_clave_secreta_para_firmar_tokens_jwt_min_256_bits
app.security.jwt-expiration-ms=86400000
app.security.card-secret-key=0123456789abcdef0123456789abcdef
```