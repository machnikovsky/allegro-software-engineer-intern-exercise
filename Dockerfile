FROM openjdk:15-alpine
ADD target/internexercise-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar internexercise-0.0.1-SNAPSHOT.jar