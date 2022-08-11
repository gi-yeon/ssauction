FROM openjdk:11-jdk

LABEL maintainer="gus4805@naver.com"

EXPOSE 8080

ARG WAR_FILE=build/libs/*.war

COPY ${WAR_FILE} app.war/

ENTRYPOINT ["java","-jar","/app.war"]