# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine AS build

# Set the working directory for the build
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml ./
COPY .mvn/ .mvn
COPY mvnw ./
RUN ./mvnw dependency:go-offline

# Copy the rest of the project and build the application
COPY src ./src
RUN ./mvnw package -DskipTests

# Use another smaller base image for the final stage
FROM openjdk:17-jdk-alpine

# Set the working directory for the final image
WORKDIR /app

# Copy only the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]