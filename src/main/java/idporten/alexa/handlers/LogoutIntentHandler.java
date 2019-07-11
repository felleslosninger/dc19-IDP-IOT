package idporten.alexa.handlers;

import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import idporten.alexa.utils.AlexaUtils;
import okhttp3.*;
import org.springframework.stereotype.Component;

@Component
public class LogoutIntentHandler {

    SpeechletResponse handleIntent(Session session) {
        System.out.println("LogoutIntentHandler");

        String accessToken = session.getUser().getAccessToken();
        System.out.println("USER:");
        System.out.println(accessToken);

        if (accessToken == null) {
            return AlexaUtils.newBasicSpeechResponse("Logout", "No access token found. You do not seem to be logged in", session, true);
        }
        try {

            OkHttpClient client = new OkHttpClient();

            String url = "https://oidc-ver1.difi.no/idporten-oidc-provider/revoke";

            RequestBody requestBody = new FormBody.Builder().add("token", accessToken).build();

            Request req = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .addHeader("Authorization", "Basic b2lkY19kaWZpX2NhbXA6N2NiMzdiNjQtMGVlZC00MWY4LWFmMTAtMGE0ZGIzZjNhOGRh")
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();

            Response response = client.newCall(req).execute();
            int status = response.code();
            System.out.println("Status: " + status);

            if (status > 299) {
                return AlexaUtils.newBasicSpeechResponse("Logout", "Something went wrong. Status code is bigger that 299.", session, true);
            } else {
                return AlexaUtils.newBasicSpeechResponse("Logout", "You are logged out! Have fun with the rest of your life.", session, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AlexaUtils.newBasicSpeechResponse("Logout", "Something went wrong with the response. Please check the stack trace or try again.", session, true);
        }
    }
}
