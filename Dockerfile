FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/trendex-0.0.1-SNAPSHOT.jar
ARG YML_FILE=src/main/resources/trendex-secrets/application-prod.yml
COPY ${JAR_FILE} /trendex.jar
COPY ${YML_FILE}/application-prod.yml /trendex-secrets/application-prod.yml
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/trendex.jar"]
