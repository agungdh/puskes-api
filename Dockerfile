FROM eclipse-temurin:25-jdk AS builder
WORKDIR /app
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline
COPY src src
RUN ./mvnw package -DskipTests

FROM gcr.io/distroless/java25-debian13
COPY --from=builder /app/target/*.jar /app/app.jar
CMD ["/app/app.jar"]
