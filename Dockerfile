# First stage: build the application
FROM maven:3.9.9-ibm-semeru-21-jammy AS build
COPY . /app
WORKDIR /app
RUN mvn package -DskipTests

FROM openjdk:18
LABEL authors="jock"
COPY --from=build /app/target/demo1-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
