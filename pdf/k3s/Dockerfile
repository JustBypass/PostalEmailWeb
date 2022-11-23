FROM openjdk:11-jre-slim
EXPOSE 8080
ADD target/pdf-jar-with-dependencies.jar pdf-jar-with-dependencies.jar
ENTRYPOINT ["java","-jar","pdf-jar-with-dependencies.jar"]
