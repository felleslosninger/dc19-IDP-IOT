package idporten.alexa.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import idporten.alexa.utils.AlexaUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
            URL url = new URL("https://oidc-ver1.difi.no/kontaktinfo-oauth2-server/rest/v1/person");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + accessToken);
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            System.out.println("Status code: " + status);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while((inputLine = in.readLine()) != null){
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            //GetURLDecoder??
            System.out.println("BASE64:\n\n");
            System.out.println(content);
            byte[] decodedBytes = Base64.getMimeDecoder().decode(content.toString());  //Base64.getDecoder().decode(content.toString());
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