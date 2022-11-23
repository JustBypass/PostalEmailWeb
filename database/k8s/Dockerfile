FROM openjdk:11-jre-slim
EXPOSE 8080
ADD target/database-spring-boot.jar database-spring-boot.jar
ENTRYPOINT ["java","-jar","database-spring-boot.jar"]
