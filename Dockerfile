# Base image containing the Java runtime
FROM openjdk:17-jdk-slim 

# Who maintains the image
LABEL MAINTAINER="Arthur"

# Copy the compiled .jar file to the container
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts.jar

# Execute the command "java -jar accounts.jar"
ENTRYPOINT [ "java", "-jar", "accounts.jar" ]