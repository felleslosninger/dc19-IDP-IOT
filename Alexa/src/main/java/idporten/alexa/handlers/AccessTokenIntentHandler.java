package idporten.alexa.handlers;

import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import idporten.alexa.utils.AlexaUtils;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenIntentHandler {

    SpeechletResponse handleIntent(Session session) {
        System.out.println("AccessTokenIntentHandler");

        String accessToken = session.getUser().getAccessToken();
        if (accessToken == null) {
            Card card = AlexaUtils.newCard("Access token", "No access token found");
            PlainTextOutputSpeech speech = AlexaUtils.newSpeech("No access token was found", AlexaUtils.inConversationMode(session));

            return AlexaUtils.newSpeechletResponse(card, speech, session, false);
        }

        Card card = AlexaUtils.newCard("Access token", accessToken);
        PlainTextOutputSpeech speech = AlexaUtils.newSpeech("I found your access token", AlexaUtils.inConversationMode(session));

        return AlexaUtils.newSpeechletResponse(card, speech, session, false);
    }
}
