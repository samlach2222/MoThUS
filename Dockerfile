#
# Build stage
#
# In Maven x.y.z, updating x or y may cause breaking changes in the future
FROM maven:3.9 AS build
COPY . .
COPY ./env.properties ./src/main/resources/env.properties
RUN mvn clean install

#
# Package stage
#
# TODO : Use 25-jdk when available
# Not latest JDK as it may cause breaking changes in the future
FROM eclipse-temurin:21-jdk
COPY --from=build /target/MoThUS-0.0.1-SNAPSHOT.jar mothus.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","mothus.jar"]
