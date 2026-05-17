FROM eclipse-temurin:21

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package

EXPOSE 8080

CMD ["java", "-jar", "target/taskmanager-0.0.1-SNAPSHOT.jar"]