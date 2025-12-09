FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Kopjojmë nga subfolder-i realestate
COPY realestate/pom.xml .
COPY realestate/src ./src

RUN apt-get update && \
    apt-get install -y maven && \
    mvn clean package -DskipTests

RUN cp target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["sh","-c","java -jar app.jar --server.port=${PORT:-8081}"]
