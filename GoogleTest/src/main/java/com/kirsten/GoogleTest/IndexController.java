package com.kirsten.GoogleTest;

import com.google.actions.api.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServlet;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@RestController
public class IndexController {
    private final Logger LOG = LoggerFactory.getLogger(MyActionsApp.class);



    private final App actionsApp = new MyActionsApp();

    //handle all incoming requests to URI "/"

    @GetMapping("/")
    String serveAck() {
        return "App is listening but requires valid POST request to respond with Action response.";
    }

    //handles post requests at URI /googleservice
    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public String serveAction(@RequestBody String body, @RequestHeader Map<String, String> headers) {
        //tar inn json string
        System.out.println("JSON body");
        System.out.println(body);
        try {
            //writeResponse(response, jsonResponse);
            //String med request body og object that has all request header entries
            String jsonResponse = actionsApp.handleRequest(body, headers).get();
            LOG.info("Generated json = {}", jsonResponse);
            return jsonResponse;

        } catch (InterruptedException | ExecutionException e) {
            return handleError(e);
        }
    }

        private String handleError(Exception e) {
            e.printStackTrace();
            LOG.error("Error in App.handleRequest ", e);
            return "Error handling the intent - " + e.getMessage();
        }
}




