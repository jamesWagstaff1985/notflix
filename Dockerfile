FROM openjdk:17-jdk-slim
MAINTAINER james wagstaff

RUN apt update && apt upgrade -y
#RUN apt install ffmpeg -y

COPY target/streaming-0.0.1-SNAPSHOT.jar streaming-server-0.0.1.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "/streaming-server-0.0.1.jar"]
