# Dificamp
Using Google Home assistant to login with IDporten

# Introduction
The purpose of this document is to provide details on how to create Actions by using a webhook API for communicating between Actions on Google and a Spring boot based fulfillment service. Dialoglow is used as webhook format. The fulfillment implemens a webhook for handeling HTTP requests from Actions, and is running in a docker container hosted in Azure. This document explains how implement the endpoint. Documentation on how to get it running in azure: [this project](https://github.com/difi/dc19-IDP-IOT/blob/master/Alexa/How%20to%20run%20Alexa%20skills%20in%20Azure.MD)


# Spring Boot
You need to add the maven dependencies in the pom.xml file. Actions on Google client library is used to provide wrapper for webhook API. Add the following:
```xml
<dependency>
    <groupId>com.google.actions</groupId>
    <artifactId>actions-on-google</artifactId>
    <version>1.2.0</version>
</dependency>

```

When the google home speaker detects speech as a query, this is converted to JSON and sendt to Dialogflow. Then Dialogflow wraps this JSON payload into the Dialogflow webhook format and sends this as a POST request to the fulfillment. The fulfillment then parses this payload and triggers the right intent in MyActionsApp. A GET request is sendt back to Dialogflow with with an response/action that is to be spoken out the user. 

To handle incoming requests from Dialogflow, you will need a restcontroller. You will need a method for handeling POST request that takes in the JSON body and a map of the headers. Use the handleRequest method to map the request to the right intent and return a JSON response. 

You will 

    
    
    

