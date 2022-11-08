FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD ./target/nome.jar nome.jar
ENTRYPOINT ["java","-jar","/nome.jar"]
