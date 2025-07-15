# Use a JDK base image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy and build the app
COPY target/*.jar app.jar

# Expose port (adjust if your app uses another port)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
