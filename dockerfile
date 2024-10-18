FROM openjdk:17-jdk-alpine
# Set the working directory inside the container
WORKDIR /app

# Copy the packaged jar file from the target directory to the app directory
COPY target/compte-0.0.1-SNAPSHOT.jar /app/compte-service.jar

# Expose the port your application runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/compte-service.jar"]
