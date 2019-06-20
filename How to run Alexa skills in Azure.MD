##Introduction
The purpose of this document is to provide details on how to create a Spring boot based Alexa skill endpoint, running in a docker contained hosted in Azure
This document does not concern itself with how to implement the Alexa endpoint in Spring boot.
This document assumes that you already have a working endpoint, and explains how to get this endpoint running on Azure.
TO gain more insight into how to implement the endpoint, see [this project](https://github.com/matsjp/alexa-chess-skill) 

##Spring boot
###application.properties
Azure will send incoming requests to your docker container on port 80, so you need to
configure your application.properties file to make the application run on port 80
![Image of application.properties](images/applicationproperties.png)
###maven
In your pom.xml file add ``<docker.image.prefix>springio</docker.image.prefix>`` to the properties part 
(springio can be replaced with whatever you want).
Add 
```
<plugin>
    <groupId>com.spotify</groupId>
    <artifactId>dockerfile-maven-plugin</artifactId>
    <version>1.4.9</version>
    <configuration>
        <repository>${docker.image.prefix}/${project.artifactId}</repository>
    </configuration>
</plugin>
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <executions>
        <execution>
            <id>unpack</id>
            <phase>package</phase>
            <goals>
                <goal>unpack</goal>
            </goals>
            <configuration>
                <artifactItems>
                    <artifactItem>
                        <groupId>${project.groupId}</groupId>
                        <artifactId>${project.artifactId}</artifactId>
                        <version>${project.version}</version>
                    </artifactItem>
                </artifactItems>
            </configuration>
        </execution>
    </executions>
</plugin>
```
under plugins

##dockerfile
In your project root create a file called Dockerfile. Put the following content into the dockerfile:


```
FROM openjdk:8-jdk-alpine
COPY ./target/azuretest-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app/
RUN sh -c "touch azuretest-0.0.1-SNAPSHOT.jar"
ENTRYPOINT ["java", "-Dcom.amazon.speech.speechlet.servlet.timestampTolerance=150", "-Dcom.amazon.speech.speechlet.servlet.supportedApplicationIds=amzn1.ask.skill.4bd2b5bd-9e6b-4a8f-9315-07c4ba308fb7", "-jar", "azuretest-0.0.1-SNAPSHOT.jar"]
```
This copy paste needs some changes. Change every instance of azuretest-0.0.1-SHANSHOT.jar with your own jar file. If you don't know your jar file, run the command ``mvnw package``
and look in the target folder for the jar file that gets generated.

You also need to change ```-Dcom.amazon.speech.speechlet.servlet.supportedApplicationIds=amzn1.ask.skill.4bd2b5bd-9e6b-4a8f-9315-07c4ba308fb7```to equal your skills skill id. This id can be found when configuring your Alexa skill

##Creating the docker image and uploading to dockerhub
###Creating the docker image
Run ```mvnw package``` to build your jar file

Run ```mvnw install dockerfile:build``` to build your docker image

###Uploading to dockerhub
Run ```docker images``` to get a list of your images. Copy the id of the image you want to upload

Run ```docker tag <image id> <dockerhub repository>:tag```

For example ```docker tag 84969686 matsjp/azuretest:latest```

Run ```docker push <dockerhub repository:tag>``` to upload the image to dockerhub

For example ```docker push matsjp/azuretest:latest```

Check dockerhub to ensure that your image got successfully uploaded

##Setting up Azure
Head to [portal.azure.com](https://portal.azure.com) and enter the appropriate group

Press create a resource
![Image of azure portal](images/azure_portal.png)

Press Web, Webapp for containers

Fill in the fields, use linux as os

When done click create. Azure will now set everything up.

In the overview for the resource you created, you can find the url of the resource. Take note of this url
![Image of url](images/resourceurl.png)

##Configuring the Alexa skill endpoint
Navigate to the page where you configure the endpoint.

Enter the url to the resource into the default region

Select that your development endpoint is a sub-domain 
![Image of endpoint](images/endpoint.png)