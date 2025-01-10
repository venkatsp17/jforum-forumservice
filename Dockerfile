# Step 1: Use an official OpenJDK image as the base image
FROM openjdk:17-jdk-slim AS build

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the jar file from the build context into the container
# Make sure the JAR file name is correct (e.g., target/forumservice-0.0.1-SNAPSHOT.jar)
COPY target/forumservice-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the port that the Spring Boot app will run on
EXPOSE 8085

# Step 5: Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
