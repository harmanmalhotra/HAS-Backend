# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.8.2-jdk-11 as build
#
# # Copy local code to the container image.
COPY . .
#
# # Build a release artifact.
RUN mvn clean package

# Use AdoptOpenJDK for base image.
# It's important to use OpenJDK 8u191 or above that has container support enabled.
# https://hub.docker.com/r/adoptopenjdk/openjdk8
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
FROM openjdk:11-jdk-slim

# Copy the jar to the production image from the builder stage.
COPY target/Hostel-Availability-System-*.jar Hostel-Availability-System.jar

# Expose Port
EXPOSE 8080

# Run the web service on container startup.
ENTRYPOINT ["java","-jar","Hostel-Availability-System.jar"]

