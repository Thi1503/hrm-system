FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/request-approval-service-*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
