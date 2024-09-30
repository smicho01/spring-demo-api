# First stage: build the JAR file
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the entire project and build the application
COPY src ./src
RUN mvn clean install -DskipTests

# Second stage: create the Docker image with the JAR file
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port on which the app runs (adjust as needed)
EXPOSE 9090

# Set the entrypoint to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]