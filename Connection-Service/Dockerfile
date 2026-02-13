# --- STAGE 1: Extract ---
FROM eclipse-temurin:21-jre-alpine AS extractor
WORKDIR /app
# This ARGS looks for the jar in your LOCAL target folder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=layertools -jar app.jar extract --destination target/extracted

# --- STAGE 2: Runtime ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN apk --no-cache add curl
COPY --from=extractor /app/target/extracted/dependencies/ ./
COPY --from=extractor /app/target/extracted/spring-boot-loader/ ./
COPY --from=extractor /app/target/extracted/snapshot-dependencies/ ./
COPY --from=extractor /app/target/extracted/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]