FROM maven:3.6-jdk-8-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY  src ./src
Run mvn -e -B package

FROM openjdk:8-jre-alpine
COPY --from=builder /app/target/*.jar app.jar
CMD ["java","-jar","/app.jar"]
