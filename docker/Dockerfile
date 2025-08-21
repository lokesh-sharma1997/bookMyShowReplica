FROM openjdk:17
WORKDIR /app
COPY target/bookmyshow-backend-*.jar  /app                              
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/bookmyshow-backend-*.jar"]