package idporten.alexa.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Permissions;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import idporten.alexa.utils.AlexaUtils;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class LogoutIntentHandler{

    public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session){
        System.out.println("LogoutIntentHandler");

        String accessToken = session.getUser().getAccessToken();
        System.out.println("USER:");
        System.out.println(accessToken);

        if(accessToken == null){
            return AlexaUtils.newBasicSpeechResponse("Logout", "No access token found. You do not seem to be logged in", session, true);
        }
        try{
            // Authorization: Basic b2lkY19kaWZpX2NhbXA6N2NiMzdiNjQtMGVlZC00MWY4LWFmMTAtMGE0ZGIzZjNhOGRh
            /* POST
               Cache-Control: no-cache
               Content-Type: application/x-www-form-urlencoded

               token=$token$*/

            OkHttpClient client = new OkHttpClient();

            String url = "https://oidc-ver1.difi.no/idporten-oidc-provider/revoke";

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("token", accessToken)
                    .build();

            Request req = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .addHeader("Authorization", "Basic b2lkY19kaWZpX2NhbXA6N2NiMzdiNjQtMGVlZC00MWY4LWFmMTAtMGE0ZGIzZjNhOGRh")
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();

            Response response = client.newCall(req).execute();
            int status = response.code();





            /*URL url = new URL("https://oidc-ver1.difi.no/idporten-oidc-provider/revoke");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Authorization", "Basic b2lkY19kaWZpX2NhbXA6N2NiMzdiNjQtMGVlZC00MWY4LWFmMTAtMGE0ZGIzZjNhOGRh");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestMethod("POST");

            String postData = "token=" + accessToken;
            byte[] postBytes = postData.getBytes(StandardCharsets.UTF_8);

            DataOutputStream dos = new DataOutputStream(con.getOutputStream());
            dos.write(postBytes);

            int status = con.getResponseCode();*/
            System.out.println("Status: " + status);

            if(status > 299){
                return AlexaUtils.newBasicSpeechResponse("Logout", "Something went wrong. Status code is bigger that 299.", session, true);
            }else{
                return AlexaUtils.newBasicSpeechResponse("Logout", "You are logged out! Have fun with the rest of your life.", session, true);
            }
        }catch(Exception e){
            e.printStackTrace();
            return AlexaUtils.newBasicSpeechResponse("Logout", "Something went wrong with the response. Please check the stack trace or try again.", session, true);
        }
    }
}
