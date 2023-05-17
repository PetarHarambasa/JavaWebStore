package hr.algebra.webshop.filter;

import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static hr.algebra.webshop.controller.AuthenticationController.authenticatedShopUser;
import static hr.algebra.webshop.model.UserRole.ADMIN;
import static hr.algebra.webshop.model.UserRole.CUSTOMER;

@Component
public class LoggedInFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(LoggedInFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        logger.info(checkUser());
        filterChain.doFilter(servletRequest, servletResponse);

    }

    private static String checkUser() {
        if (authenticatedShopUser.getIdShopUser() != null) {
            return "User logged in: " + authenticatedShopUser.getEmail() + " " + (authenticatedShopUser.getUserRoleId() == 1 ? ADMIN : CUSTOMER);
        }
        return "No user logged in";
    }
}
