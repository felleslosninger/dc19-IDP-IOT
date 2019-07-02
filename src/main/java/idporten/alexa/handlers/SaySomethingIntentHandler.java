package idporten.alexa.handlers;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import idporten.alexa.utils.AlexaUtils;
import org.springframework.stereotype.Component;

@Component
public class SaySomethingIntentHandler{

    public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session){
        System.out.println("Handling intent");

        Card card = AlexaUtils.newCard("Java Demo", "Hello Alexa, what's up?");
        PlainTextOutputSpeech speech = AlexaUtils.newSpeech("Hello Alexa, what's up?", AlexaUtils.inConversationMode(session));

        return AlexaUtils.newSpeechletResponse(card, speech, session, false);
    }
}
