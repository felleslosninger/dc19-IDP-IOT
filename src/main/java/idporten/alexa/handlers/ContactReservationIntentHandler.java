package idporten.alexa.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import idporten.alexa.utils.AlexaUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Base64;

@Component
public class ContactReservationIntentHandler{

    public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session){
        System.out.println("ContactReservationIntentHandler");

        String accessToken = session.getUser().getAccessToken();
        //System.out.println("Contact Reservation registry:");
        //System.out.println(accessToken);

        //https://oidc-ver1.difi.no/kontaktinfo-oauth2-server/rest/v1/person

        if(accessToken == null){
            return AlexaUtils.newBasicSpeechResponse("Contact Reservation registry", "No access token found. Cannot contact the API.", session, false);
        }
        try{
            OkHttpClient client = new OkHttpClient();

            String url = "https://oidc-ver1.difi.no/kontaktinfo-oauth2-server/rest/v1/person";

            Request req = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();

            Response response = client.newCall(req).execute();
            String content = response.body().string();
            System.out.println("Payload");
            System.out.println(content);


            JSONObject json = new JSONObject(content);

            String returnText;
            if(json.getString("reservasjon").equals("NEI")){
                returnText = "You are not reserved from digital contact";
            }else{
                returnText = "You are reserved from digital contact";
            }

            return AlexaUtils.newBasicSpeechResponse("Contact Reservation registry", returnText, session, false);
        }catch(Exception e){
            e.printStackTrace();
            return AlexaUtils.newBasicSpeechResponse("Contact Reservation registry", String.format("Something went wrong with the response. Please check the stack trace or try again. %s", e.toString()), session, false);
        }
    }
}

