package hr.algebra.webshop.listener;

import jakarta.servlet.http.HttpSessionEvent;
import org.springframework.stereotype.Component;

@Component
public class HttpSessionListener implements jakarta.servlet.http.HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session created!");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("Session destroyed!");
    }
}
