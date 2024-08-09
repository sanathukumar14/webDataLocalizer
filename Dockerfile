# Use an official Ubuntu image as the base
FROM ubuntu:latest

# Install OpenJDK 17
RUN apt update && apt install -y openjdk-17-jdk

# Install Maven
RUN apt update && apt install -y maven

# Set the working directory to /app
WORKDIR /app

# Copy the Maven dependencies (pom.xml) and download the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the application code
COPY . .

# Compile the application
RUN mvn clean package -DskipTests -Dmaven.compiler.release=17

# Make port 8090 available to the world outside this container
EXPOSE 8090

# Run the application when the container launches
CMD ["java", "-jar", "target/webDataLocalizer-0.0.1-SNAPSHOT.jar"]