package hr.algebra.webshop.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static hr.algebra.webshop.controller.AuthenticationController.authenticatedShopUser;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        return "errorPage";
    }
}
