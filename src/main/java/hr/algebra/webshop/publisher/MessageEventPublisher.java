package hr.algebra.webshop.publisher;

import hr.algebra.webshop.event.LoginMessageEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MessageEventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    public MessageEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishCustomEvent(final String message) {
        System.out.println("Publishing event.");
        LoginMessageEvent loginMessageEvent = new LoginMessageEvent(this, message);
        applicationEventPublisher.publishEvent(loginMessageEvent);
    }
}
