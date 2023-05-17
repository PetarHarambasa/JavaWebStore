package hr.algebra.webshop.event;

import org.springframework.context.ApplicationEvent;

public class LoginMessageEvent extends ApplicationEvent {
    private String message;

    public LoginMessageEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
