#Construcción (Builder)
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

#Copiar archivos del wrapper de Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

#Dar permisos de ejecución al script gradlew
RUN chmod +x ./gradlew

#Descargar dependencias en caché
RUN ./gradlew dependencies --no-daemon || true

#Copiar el código fuente
COPY src src

#Compilar y empaquetar el JAR omitiendo los tests para acelerar el build
RUN ./gradlew bootJar --no-daemon -x test

# Etapa 2: Imagen de Ejecución (Runtime)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

#Crear un usuario no-root por seguridad
RUN addgroup -S cavoshgroup && adduser -S cavoshuser -G cavoshgroup
USER cavoshuser

#Copiar el JAR generado desde la etapa de construcción
COPY --from=builder /app/build/libs/*.jar app.jar

#Exponer el puerto de Spring Boot
EXPOSE 8080

#Optimización de memoria en contenedores
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]