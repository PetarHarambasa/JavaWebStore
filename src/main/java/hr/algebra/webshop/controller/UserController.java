package hr.algebra.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static hr.algebra.webshop.controller.AuthenticationController.authenticatedShopUser;

@Controller
public class UserController {

    @GetMapping("userHistory")
    public String getUserHistory(Model model) {
        if (authenticatedShopUser.isAuthenticated()) {
            model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
            return "userHistory";
        }
        return "redirect:dragonBallStore";
    }
}
