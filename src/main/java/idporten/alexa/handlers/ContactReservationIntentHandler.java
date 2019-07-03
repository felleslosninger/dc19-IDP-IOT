package idporten.alexa.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
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
import java.util.Iterator;

@Component
public class ContactReservationIntentHandler{

    public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session){
        System.out.println("ContactReservationIntentHandler");

        String accessToken = session.getUser().getAccessToken();
        System.out.println("Contact Reservation registry:");
        System.out.println(accessToken);

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





            //GetURLDecoder??
            System.out.println("BASE64:\n\n");
            System.out.println(content);
            byte[] decodedBytes = Base64.getMimeDecoder().decode(content);  //Base64.getDecoder().decode(content.toString());
            String decodedContent = new String(decodedBytes);
            System.out.println("DECODED:\n\n");
            System.out.println(decodedContent);

            String jsonString = decodedContent.substring(decodedContent.indexOf('}')+1, decodedContent.lastIndexOf('}'));

            JSONObject json = new JSONObject(jsonString);
            Iterator<String> it = json.keys();
            /*
            System.out.println("Keys coming:");
            while(it.hasNext()){
                System.out.println("key");
                String key = it.next();
                System.out.println(key);
            }*/
            String reservasjon = json.getString("reservasjon");
            System.out.println(reservasjon);
            String returnText;
            if(reservasjon.equals("NEI")){
                returnText = "You are not reserved from digital contact";
            }else{
                returnText = "You are reserved from digital contact";
            }

            return AlexaUtils.newBasicSpeechResponse("Contact Reservation registry", returnText, session, false);


        }catch(Exception e){
            e.printStackTrace();
            return AlexaUtils.newBasicSpeechResponse("Contact Reservation registry", "Something went wrong with the response. Please check the stack trace or try again.", session, false);
        }



    }
}