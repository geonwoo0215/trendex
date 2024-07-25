FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/trendex-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /trendex.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/trendex.jar"]
