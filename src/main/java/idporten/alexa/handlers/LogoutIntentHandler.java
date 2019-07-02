package idporten.alexa.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Permissions;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import idporten.alexa.utils.AlexaUtils;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class LogoutIntentHandler{

    public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session){
        System.out.println("LogoutIntentHandler");

        String accessToken = session.getUser().getAccessToken();
        System.out.println("USER:");

        Permissions permissions = session.getUser().getPermissions();
        String consentToken = permissions.getConsentToken();
        System.out.println(consentToken);

        if(accessToken == null){
            return AlexaUtils.newBasicSpeechResponse("Logout", "No access token found. You do not seem to be logged in", session, true);
        }
        try{
            /*
            URL url = new URL("https://oidc-ver1.difi.no/idporten-oidc-provider/revoke");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Authorization", "Bearer " + accessToken);
            con.setRequestMethod("POST");*/
            return AlexaUtils.newBasicSpeechResponse("Logout", "Have fun logging out lol", session, true);
        }catch(Exception e){
            e.printStackTrace();
            return AlexaUtils.newBasicSpeechResponse("Logout", "Something went wrong with the response. Please check the stack trace or try again.", session, true);
        }
    }
}
