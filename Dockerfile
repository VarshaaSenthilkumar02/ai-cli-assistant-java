# FROM tells Docker what base image to start building from.
#  In my AI Assistant project, I don't want to install Java myself every time.
# So I extend an existing image that already has Java 25 installed.
#  Then I build my application on top of that image by adding my JAR file and telling Docker how to run it.

FROM eclipse-temurin:25-jdk

# WORKDIR sets the current working directory inside the Docker container.
# All subsequent instructions like COPY, RUN, and CMD execute relative to this directory.
# It makes the Dockerfile cleaner because I don't have to repeatedly specify absolute paths.

WORKDIR /app

COPY target/ai-cli-assistant.jar app.jar

CMD ["java", "-jar", "app.jar"]