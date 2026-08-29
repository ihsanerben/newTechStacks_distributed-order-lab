FROM maven:3.9.11-eclipse-temurin-21-alpine AS build
WORKDIR /workspace
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw -q -DskipTests dependency:go-offline
COPY src src
RUN ./mvnw -q -DskipTests package

FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S spring && adduser -S spring -G spring
WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar
USER spring:spring
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
