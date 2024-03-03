# Use an OpenJDK base image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/medical_uservice-1.0-SNAPSHOT.jar /app/medical_uservice-1.0-SNAPSHOT.jar

# Expose the port that the application runs on
EXPOSE 8080

# Run the JAR file when the container launches
CMD ["java", "-jar", "medical_uservice-1.0-SNAPSHOT.jar", "--spring.data.mongodb.uri=mongodb://mongo:27017/patientdb"]
