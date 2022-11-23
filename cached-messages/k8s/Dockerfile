FROM openjdk:11-jre-slim
EXPOSE 8080
ADD target/cached-jar-with-dependencies.jar cached-jar-with-dependencies.jar
ENTRYPOINT ["java","-jar","cached-jar-with-dependencies.jar"]
