# Dificamp
Using Google Home assistant to login with IDporten

# Introduction
The purpose of this document is to provide details on how to create Actions by using a webhook API for communicating between Actions on Google and a Spring boot based fulfillment service. This implementation uses the [Dialoglow webhook format](https://developers.google.com/actions/build/json/dialogflow-webhook-json). The fulfillment implemens a webhook for handling HTTP requests from Actions, and is running in a docker container hosted on Azure. This document explains how to implement the endpoint. Documentation on how to get it running in azure: [this project](https://github.com/difi/dc19-IDP-IOT/blob/master/Alexa/How%20to%20run%20Alexa%20skills%20in%20Azure.MD)


# Spring Boot
You need to add the maven dependencies in the pom.xml file. The Actions on Google library is used to provide wrapper for webhook API. Add the following:
```xml
<dependency>
    <groupId>com.google.actions</groupId>
    <artifactId>actions-on-google</artifactId>
    <version>1.2.0</version>
</dependency>

```

When the google home speaker detects speech as a query, this is converted to JSON and sendt to Dialogflow. Then Dialogflow wraps this JSON payload into the Dialogflow webhook format and sends this as a POST request to the fulfillment. The fulfillment then parses this payload and triggers the right intent in MyActionsApp. A GET request is sendt back to Dialogflow with a response that is to be spoken out the user. 

To handle incoming requests from Dialogflow, you will need a rest controller. You will need a method for handling POST request that takes in the JSON body and a map of the headers. Use the handleRequest method to map the request to the right intent and return a JSON response. 

You will 

    
    
    

