package idporten.alexa.handlers;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.LinkAccountCard;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import idporten.alexa.utils.AlexaUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HandlerSpeechlet implements SpeechletV2{

    @Autowired
    private BeanFactory beanFactory;

    public HandlerSpeechlet(){

    }

    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope){

    }

    @Override
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope){
        System.out.println("Launched the Alexa Application!");

        Session session = requestEnvelope.getSession();
        AlexaUtils.setConversationMode(session, true);

        // Create the initial greeting speech.
        String speechText = "Welcome to the ID port. Please log in to proceed.";

        return AlexaUtils.newBasicSpeechResponse("Launched ID Port", speechText, session, false);
    }

    @Override
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope){
        try{
            System.out.println("In onIntent");
            IntentRequest request = requestEnvelope.getRequest();
            Session session = requestEnvelope.getSession();

            Intent intent = request.getIntent();
            String intentName = intent.getName();

            if(intentName.equals("SaySomethingIntent")){
                System.out.println("Saying something");
                SaySomethingIntentHandler ssih = new SaySomethingIntentHandler();
                return ssih.handleIntent(intent, request, session);
            }else if(intentName.equals("AccessTokenIntent")){
                System.out.println("Access token");
                AccessTokenIntentHandler atih = new AccessTokenIntentHandler();
                return atih.handleIntent(intent, request, session);
            }else if(intentName.equals("ContactReservationIntent")){
                System.out.println("Contact Reservation");
                ContactReservationIntentHandler crih = new ContactReservationIntentHandler();
                return crih.handleIntent(intent, request, session);
            }else if(intentName.equals("LoginIntent")){
                System.out.println("Login");
                LoginIntentHandler lih = new LoginIntentHandler();
                return lih.handleIntent(intent, request, session);
            }
            else if(intentName.equals("LogoutIntent")){
                System.out.println("Logout");
                LogoutIntentHandler lih = new LogoutIntentHandler();
                return lih.handleIntent(intent, request, session);
            }
            else{
                System.out.println("Not gonna say something");
            }
            /*
            String handlerBeanName = intentName + "Handler";
            Object handlerBean = beanFactory.getBean(handlerBeanName);

            IntentHandler intentHandler = (IntentHandler) handlerBean;
            return intentHandler.handleIntent(intent, request, session);*/
        }catch(Exception e){
            e.printStackTrace();
        }
        return new SpeechletResponse();
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope){

    }
}
