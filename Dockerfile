# usar imagen base de java 21
FROM eclipse-temurin:21-jdk-alpine

#directorio de trabajo
WORKDIR /app

#copiar el archivo pom.xml y el wrapper de maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

#dar permisos al mvnw
RUN chmod +x ./mvnw

#descargar dependecias
RUN ./mvnw dependency:go-offline -B

#copiar codigo fuente
COPY src src

#construir el proyecto
RUN ./mvnw package -DskipTests

#exponer el puerto
EXPOSE 8083

#ejecutar el jar
ENTRYPOINT ^["java", "-jar", "target/notificaciones-service-0.0.1SNAPSHOT.jar"]
