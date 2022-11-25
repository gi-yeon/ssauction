FROM openjdk:11-jdk

LABEL maintainer="maintainer 이메일 개인정보 삭제"

EXPOSE 8080

ARG JAR_FILE=build/libs/*.war

COPY ${JAR_FILE} app.war

ENTRYPOINT ["java","-jar","/app.war"]

