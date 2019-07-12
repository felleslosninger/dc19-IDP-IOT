package idporten.alexa.config;


import com.amazon.speech.speechlet.servlet.SpeechletServlet;
import idporten.alexa.handlers.HandlerSpeechlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlexaConfig {

    private final HandlerSpeechlet handlerSpeechlet;

    public AlexaConfig(HandlerSpeechlet handlerSpeechlet) {
        this.handlerSpeechlet = handlerSpeechlet;
    }

    @Bean
    public ServletRegistrationBean<SpeechletServlet> registerSpeechletServlet() {
        SpeechletServlet speechletServlet = new SpeechletServlet();
        speechletServlet.setSpeechlet(handlerSpeechlet);

        return new ServletRegistrationBean<>(speechletServlet, "/alexa");
    }
}
