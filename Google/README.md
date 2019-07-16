# Dificamp
Using Google Home assistant to login with IDporten

### Introduction
The purpose of this document is to provide details on how to create Actions by using a webhook API for communicating between Actions on Google and a Spring boot based fulfillment service. This implementation uses the [Dialoglow webhook format](https://developers.google.com/actions/build/json/dialogflow-webhook-json). The fulfillment implemens a webhook for handling HTTP requests from Actions, and is running in a docker container hosted on Azure. This document explains how to implement the endpoint. It is recomended to see documentation on how to get you application running in Azure before continuing: [this project](https://github.com/difi/dc19-IDP-IOT/blob/master/Alexa/How%20to%20run%20Alexa%20skills%20in%20Azure.MD)

### Dialogflow
In the google actions console create a new Action. Use Dialogflow to build a conversation interface. In the fulfillment section enable "webhook" and add the URL of your web service. This example uses the URL of the endpoint hosted by Azure, https://googleactiontest.azurewebsites.net. For every intent that should be handled in the fulfillment, remember to enable "webhook call for this intent" when creating a new intent.  

##### Dialogflow requests and responses
When the google home speaker detects speech as a query, this is converted to JSON and sendt to Dialogflow. Then Dialogflow wraps this JSON payload into the Dialogflow webhook format and sends this as a POST request to the fulfillment. The fulfillment then parses this payload and triggers the right intent in MyActionsApp. A GET request is sendt back to Dialogflow with a response that is to be spoken out the user. 

### Spring Boot
You need to add the maven dependencies in the pom.xml file. The Actions on Google library is used to provide wrapper for webhook API. Add the following:

```xml
<dependency>
    <groupId>com.google.actions</groupId>
    <artifactId>actions-on-google</artifactId>
    <version>1.2.0</version>
</dependency>

```
To handle incoming requests to fulfillment endpoint, create a rest controller. You will need to have a MyActionsApp class that extends DialogFlowApp to handle intents. Every function that handles an intent must have @ForIntent("Intent name") at the top, where intent name is the same as the intent name in Dialogflow. You will need a method for handling POST request that takes in the JSON body and a map of the headers. To map the request to the right intent, use the handleRequest method that passes the body to the handleRequest method in the MyActionsApp. The handleRequest method checks the annotations of every method in the MyActionsApp to see if the intent name matches the name specified in the request. If a match is found, this method will be invoked and the restcontroller returns a resulting JSON response. 

### Accout linking
To set up account linking go to Actions Console and select you project [Actions Console](https://console.actions.google.com). In developement, go to Account linking and follow the steps by adding client ID, Client secret, scopes authorization URL and token URL.

### Summary
If you have everything set up configured in azure and go to test environment and see how it is working. By downloading the Google Home app on your smart phone you can test your Action on you Google Home device. Remember to enable no screen output, in the screen capabilities in Actions Console. You Action should be ready to be tested in no time. Have fun!



    
    
    

