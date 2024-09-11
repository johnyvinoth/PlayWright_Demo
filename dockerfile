# Use an official Maven image from the Docker Hub
# FROM maven:3-openjdk-17
# Start with an Ubuntu base image
FROM ubuntu:20.04

# Install necessary packages
RUN apt-get update && \
    apt-get install -y \
    openjdk-17-jdk \
    maven \
    git \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory in the container
WORKDIR /app

# Clone the GitHub repository
RUN git clone https://github.com/johnyvinoth/PlayWright_Demo.git .

# Copy your project files into the container
COPY pom.xml ./
COPY src ./src

# Download Maven dependencies (including Playwright and TestNG)
RUN mvn dependency:go-offline

# Build the project
RUN mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install-deps"
RUN mvn clean package

# Run the JAR file
CMD ["java", "-jar", "target/PlayWright_Demo-1.0-SNAPSHOT.jar"]
