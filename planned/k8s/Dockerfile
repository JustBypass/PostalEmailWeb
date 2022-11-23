FROM openjdk:11-jre-slim
EXPOSE 8080
ADD target/planned-jar-with-dependencies.jar planned-jar-with-dependencies.jar
ENTRYPOINT ["java","-jar","planned-jar-with-dependencies.jar"]
