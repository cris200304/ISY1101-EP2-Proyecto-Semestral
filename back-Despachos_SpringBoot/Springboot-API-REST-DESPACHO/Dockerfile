# Etapa de compilación
FROM maven:3.9.8-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jre

RUN addgroup --system appgroup && adduser --system appuser --ingroup appgroup

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

USER appuser

EXPOSE 8081

ENTRYPOINT ["java","-jar","app.jar"]