FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/payroll-service-*.jar app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar"]
