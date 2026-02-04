# --- STAGE 1: Build ---
FROM maven:3.9.4-eclipse-temurin-21-alpine AS builder
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

# 1. We keep this to catch the bulk of dependencies
RUN ./mvnw dependency:go-offline -B

COPY src ./src

# 2. REMOVE the -o flag here so Maven can grab missing
# bits like byte-buddy or spring-security-crypto if needed.
RUN ./mvnw package -DskipTests

# --- STAGE 2: Runtime ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

USER root
RUN apk --no-cache add curl

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]