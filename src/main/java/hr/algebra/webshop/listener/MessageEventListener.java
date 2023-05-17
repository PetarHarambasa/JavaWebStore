package hr.algebra.webshop.listener;

import hr.algebra.webshop.event.LoginMessageEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MessageEventListener implements ApplicationListener<LoginMessageEvent> {
    @Override
    public void onApplicationEvent(LoginMessageEvent event) {
        System.out.println("Recived spring event - " + event.getMessage());
    }
}
