package com.kirsten.GoogleTest;

import com.google.actions.api.*;
import com.google.actions.api.response.ResponseBuilder;
import com.google.actions.api.response.helperintent.Permission;
import com.google.actions.api.response.helperintent.SignIn;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ResourceBundle;


@Component
public class MyActionsApp extends DialogflowApp {


    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(MyActionsApp.class);

    @ForIntent("Default Welcome Intent")
    public ActionResponse welcome(ActionRequest request) {
        LOGGER.info("Welcome intent start.");
        ResponseBuilder response = getResponseBuilder(request);
        if (request.getUser().getUserVerificationStatus().equals("VERIFIED")) {
            response.add(formatResponse("greet_user"));
        } else {
            Permission permission =
                    new Permission()
                            .setContext(formatResponse("permission_reason"))
                            .setPermissions(new String[]{ConstantsKt.PERMISSION_NAME});
            response.add("PLACEHOLDER_FOR_PERMISSION");
            response.add(permission);
        }
        return response.build();
    }


    @ForIntent("HelloWorld")
    public ActionResponse helloIntent(ActionRequest request) {
        LOGGER.info("Welcome to Hello World");
        System.out.println("Helloworld intent triggered");
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        responseBuilder.add("Hello to Kirstens worldddddddd suckas");
        LOGGER.info("Helloworld intent end.");
        return responseBuilder.build();
    }

    @ForIntent("bye")
    public ActionResponse bye(ActionRequest request) {
        LOGGER.info("Bye intent start.");
        System.out.println("Godbye intent triggered");
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        ResourceBundle rb = ResourceBundle.getBundle("resources");

        responseBuilder.add(rb.getString("bye")).endConversation();
        LOGGER.info("Bye intent end.");
        return responseBuilder.build();
    }

    private String readResponse(String response) {
        ResourceBundle bundle = ResourceBundle.getBundle("resources");
        return bundle.getString(response);
    }

    private String formatResponse(String response) {
        return readResponse(response);
    }

    @ForIntent("Sign in")
    public ActionResponse signIn(ActionRequest request) {
        LOGGER.info("Sign in intent started");
        ResponseBuilder responseBuilder = getResponseBuilder(request);

        return responseBuilder.add("This is the SignIn helper intent").add(new SignIn()).build();

    }


    @ForIntent("Check Reservation")
    public ActionResponse checkReservation(ActionRequest request) {
        System.out.println("ContactReservationIntentHandler");
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        String accessToken = request.getUser().getAccessToken();
        boolean signedIn = request.isSignInGranted();
        if (accessToken == null) {
            return responseBuilder
                    .add("You have no access token. Please sign inn.")
                    .build();

        }
        try {
            LOGGER.info("Reservation intent started");
            String url = "https://oidc-ver1.difi.no/kontaktinfo-oauth2-server/rest/v1/person";
            OkHttpClient client = new OkHttpClient();

            Request req = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();

            Response response = client.newCall(req).execute();
            String jsonData = response.body().string();
            JSONObject Jobject = new JSONObject(jsonData);

            String reservasjon = null;
            String returnText;
            System.out.println("Payload");
            System.out.println(jsonData);

            if (Jobject.getString("reservasjon").equals("NEI")) {
                returnText = "You are not reserved from digital contact";
            } else {
                returnText = "You are reserved from digital contact";
            }
            return responseBuilder
                    .add(returnText)
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
            return responseBuilder
                    .add("This is not working. ")
                    .build();

        }


    }
}
