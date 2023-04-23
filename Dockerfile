# # define base docker image
# FROM openjdk:11
# LABEL maintainer = "javaguides.net"
# ADD target/Hostel-Availability-System-0.0.1-SNAPSHOT.jar hostelAvailabilitySystem.jar
# ENTRYPOINT ["java", "-jar", "hostelAvailabilitySystem.jar"]



#
# Build stage
#
FROM maven:3.8.2-jdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:11-jdk-slim
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]