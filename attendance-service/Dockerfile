FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/attendance-service-*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
