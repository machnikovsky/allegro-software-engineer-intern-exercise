# Allegro software engineer intern exercise
The main goal of the exercise is to make an application, that allows you to list GitHub repositories of specified users (names and stars) and to get the total number of stars of a certain user. This data is supposed to be retrieved via HTTP protocol.

## How to run application 

There are few ways to run that application. The first one is to just clone a repository, open it in IDE and run, so I'm not going to explain it any farther. Instead, I'll explain how to run application from generated JAR file and by Docker.

### JAR file

1. Clone repository and move to the cloned directory
```
$ git clone https://github.com/machnikovsky/allegro-software-engineer-intern-exercise.git
$ cd ./allegro-software-engineer-intern-exercise
```

2. Generate JAR file by Maven. Make sure you have Maven downloaded and added to PATH. This will create a folder './target' and JAR file inside of it.
```
$ mvn install
```
3. Run JAR file. Make sure second step succeeded and make sure you have JDK installed and added to the PATH.
```
$ java -jar .\target\internexercise-0.0.1-SNAPSHOT.jar
```

### Docker

For this option you have to have Docker installed. You can either do it by Dockerfile that is inside this repository, or download an image from Dockerhub. I will explain both options.

#### Dockerfile

1. Clone repository and move to the cloned directory
```
$ git clone https://github.com/machnikovsky/allegro-software-engineer-intern-exercise.git
$ cd ./allegro-software-engineer-intern-exercise
```
2. Generate JAR file by Maven. Make sure you have Maven downloaded and added to PATH. This will create a folder './target' and JAR file inside of it.
```
$ mvn install
```
3. Build a Docker image. The tag name is up to you, but if you name it any different, make sure to use that name in a next step.
```
$ docker build -t allegro-intern-exercise:1 .
```
4. Make sure Docker image was created.
```
$ docker images
```
5. Run a Docker container. '-d' flag stands for detached, so if you want to see logs, just remove it. '-p' stands for port, so you specify here via which port you want to communicate with container. 8080 after semicolon is exposed port, so you can't change it, but you can change the first port and talk to container with the specified port. If you named your image any different, use the name you used in step 3.
```
$ docker run -d -p 8080:8080 allegro-intern-exercise:1
```

#### Dockerhub

1. Pull the image from Dockerhub.
```
$ docker pull machnikovsky/allegro-intern-exercise:latest
```
2. Run a Docker container. '-d' flag stands for detached, so if you want to see logs, just remove it. '-p' stands for port, so you specify here via which port you want to communicate with container. 8080 after semicolon is exposed port, so you can't change it, but you can change the first port and talk to container with the specified port.
```
$ docker run -d -p 8080:8080 machnikovsky/allegro-intern-exercise:latest
``` 


## About an application

Application has two main endpoints, through which user can get certain data. They are

- '/repos/{user}' - returns list of repositories of a specified in '{user}' place user. They are returned in a form of JSON with two keys: name and stars.
- '/stars/{user}' - returns the total number of stars of a specified in '{user}' place user. They are returned in a form of a single value.


## Technologies used 
<img src="./images/logos/java-logo.png" width="40" height="40"><img src="./images/logos/spring-logo.png" width="35" height="35"><img src="./images/logos/react-logo.png" width="50" height="40"><img src="./images/logos/docker-logo.png" width="45" height="35">

The backbone of the project is Spring Boot application responsible for fetching data from GitHub API and extracting only required information from recieved JSON. There is also a React app with really simple UI, so you can test Spring API easily. There is also used Docker, so you can build your image of the application and run it in a Docker container. 
