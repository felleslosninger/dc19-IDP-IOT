package idporten.alexa.handlers;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import idporten.alexa.utils.AlexaUtils;
import org.springframework.stereotype.Service;

@Service
public class HandlerSpeechlet implements SpeechletV2 {

    public HandlerSpeechlet() {

    }

    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {

    }

    @Override
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
        System.out.println("Launched the Alexa Application!");

        Session session = requestEnvelope.getSession();
        AlexaUtils.setConversationMode(session, true);

        // Create the initial greeting speech.
        String speechText = "Welcome to the ID port. Please log in to proceed.";

        return AlexaUtils.newBasicSpeechResponse("Launched ID Port", speechText, session, false);
    }

    @Override
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        try {
            System.out.println("In onIntent");
            IntentRequest request = requestEnvelope.getRequest();
            Session session = requestEnvelope.getSession();

            Intent intent = request.getIntent();
            String intentName = intent.getName();

            switch (intentName) {
                case "SaySomethingIntent":
                    System.out.println("Saying something");
                    SaySomethingIntentHandler ssih = new SaySomethingIntentHandler();
                    return ssih.handleIntent(session);
                case "AccessTokenIntent":
                    System.out.println("Access token");
                    AccessTokenIntentHandler atih = new AccessTokenIntentHandler();
                    return atih.handleIntent(session);
                case "ContactReservationIntent":
                    System.out.println("Contact Reservation");
                    ContactReservationIntentHandler crih = new ContactReservationIntentHandler();
                    return crih.handleIntent(session);
                case "LoginIntent": {
                    System.out.println("Login");
                    LoginIntentHandler lih = new LoginIntentHandler();
                    return lih.handleIntent(session);
                }
                case "LogoutIntent": {
                    System.out.println("Logout");
                    LogoutIntentHandler lih = new LogoutIntentHandler();
                    return lih.handleIntent(session);
                }
                default:
                    System.out.println("Not gonna say something");
                    break;
            }
            /*
            String handlerBeanName = intentName + "Handler";
            Object handlerBean = beanFactory.getBean(handlerBeanName);

            IntentHandler intentHandler = (IntentHandler) handlerBean;
            return intentHandler.handleIntent(intent, request, session);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SpeechletResponse();
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {

    }
}
