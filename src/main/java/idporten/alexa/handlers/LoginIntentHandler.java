package idporten.alexa.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.LinkAccountCard;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import idporten.alexa.utils.AlexaUtils;
import org.springframework.stereotype.Component;

@Component
public class LoginIntentHandler{

    public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session){
        System.out.println("LoginIntent");

        String speechText = AlexaUtils.LoginText;
        LinkAccountCard linkAccountCard = new LinkAccountCard();

        PlainTextOutputSpeech speech = AlexaUtils.newSpeech(speechText, false);

        return AlexaUtils.newSpeechletResponse(linkAccountCard, speech, session, false);
    }
}
