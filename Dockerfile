# TODO : Use 25 when available
# Not latest Java version as it may cause breaking changes in the future
ARG JAVA_VERSION=21

#
# Package stage
#
FROM eclipse-temurin:${JAVA_VERSION}-jdk AS build
COPY . .
COPY ./env.properties ./src/main/resources/env.properties
# Use Maven Wrapper instead of pulling Maven image
RUN mvnw clean package

#
# Run stage
#
FROM eclipse-temurin:${JAVA_VERSION}-jre
COPY --from=build /target/MoThUS-0.0.1-SNAPSHOT.jar mothus.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","mothus.jar"]
