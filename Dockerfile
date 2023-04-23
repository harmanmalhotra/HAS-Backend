# define base docker image
FROM openjdk:11
LABEL maintainer = "javaguides.net"
ADD target/Hostel-Availability-System-0.0.1-SNAPSHOT.jar hostelAvailabilitySystem.jar
ENTRYPOINT ["java", "-jar", "hostelAvailabilitySystem.jar"]