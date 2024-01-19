#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
COPY . .
COPY ./env.properties ./src/main/resources/env.properties
RUN mvn clean install

#
# Package stage
#
FROM eclipse-temurin:17-jdk
COPY --from=build /target/MoThUS-0.0.1-SNAPSHOT.jar mothus.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","mothus.jar"]
