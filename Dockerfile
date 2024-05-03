FROM amazoncorretto:21-al2023-jdk as builder

WORKDIR /app
COPY build.gradle settings.gradle /app/
COPY src /app/src
COPY gradlew /app/
COPY gradle /app/gradle

RUN yum install -y findutils
RUN chmod +x ./gradlew && ./gradlew bootJar

FROM amazoncorretto:21-alpine
MAINTAINER Waschndolos

COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]