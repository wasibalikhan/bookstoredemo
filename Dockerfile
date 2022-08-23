FROM openjdk:11
EXPOSE 8085
ADD target/book-store-docker.jar book-store-docker.jar
ENTRYPOINT ["java","-jar","/book-store-docker.jar"]