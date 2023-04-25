#
# Build stage
#
FROM maven:3.8-openjdk-17 AS build
COPY . .
RUN mvn clean package

#
# Package stage
#
FROM ibm-semeru-runtimes:open-17-jre-centos7
COPY --from=build /target/*.jar Hostel-Availability-System.jar
# ENV PORT=8080
# EXPOSE 8080
ENTRYPOINT ["java","-jar","Hostel-Availability-System.jar"]
