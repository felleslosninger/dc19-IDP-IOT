package idporten.alexa.utils;

import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.StandardCard;

public class AlexaUtils {

    private static final String SESSION_CONVERSATION_FLAG = "conversation";

    private static final String RepromptText = "What else can I tell you?  Say \"Help\" for some suggestions.";
    public static final String LoginText = "Please log in.";

    public static Card newCard(String cardTitle, String cardText) {
        StandardCard card = new StandardCard();
        card.setTitle((cardTitle == null) ? "IdPorten" : cardTitle);
        card.setText(cardText);

        return card;
    }

    public static PlainTextOutputSpeech newSpeech(String speechText, boolean appendRepromptText) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(appendRepromptText ? speechText + "\n\n" + AlexaUtils.RepromptText : speechText);

        return speech;
    }

    public static SpeechletResponse newSpeechletResponse(Card card, PlainTextOutputSpeech speech, Session session, boolean shouldEndSession) {

        if (AlexaUtils.inConversationMode(session) && !shouldEndSession) {
            PlainTextOutputSpeech repromtSpeech = new PlainTextOutputSpeech();
            repromtSpeech.setText(AlexaUtils.RepromptText);

            Reprompt reprompt = new Reprompt();
            reprompt.setOutputSpeech(repromtSpeech);

            return SpeechletResponse.newAskResponse(speech, reprompt, card);
        } else {
            return SpeechletResponse.newTellResponse(speech, card);
        }
    }

    public static SpeechletResponse newBasicSpeechResponse(String title, String speechText, Session session, boolean shouldEndSession) {
        Card card = AlexaUtils.newCard(title, speechText);
        PlainTextOutputSpeech speech = AlexaUtils.newSpeech(speechText, AlexaUtils.inConversationMode(session));

        return AlexaUtils.newSpeechletResponse(card, speech, session, shouldEndSession);
    }

    public static boolean inConversationMode(Session session) {
        return session.getAttribute(SESSION_CONVERSATION_FLAG) != null;
    }

    public static void setConversationMode(Session session, boolean conversationMode) {
        if (conversationMode) {
            session.setAttribute(SESSION_CONVERSATION_FLAG, "true");
        } else {
            session.removeAttribute(SESSION_CONVERSATION_FLAG);
        }
    }
}
