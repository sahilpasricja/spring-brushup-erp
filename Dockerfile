#source https://levelup.gitconnected.com/dockerizing-spring-boot-mysql-application-73e09a485c0a

#Define docker image
FROM openjdk:11
LABEL maintainer="Sahi Pasricha"

#place jar in dockr conatiner with
ADD target/spring-boot-erp-0.0.1-SNAPSHOT.jar spring-boot-erp.jar
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar

#How this app would execute in docker container
ENTRYPOINT ["java","-jar","spring-boot-erp.jar"]