package hr.algebra.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static hr.algebra.webshop.controller.AuthenticationController.authenticated;

@Controller
public class DragonBallMerchController {
    @GetMapping("/dragonBallStore")
    public String getItems(Model model){
        model.addAttribute("CheckAuth", authenticated);
        return "dragonBallItems";
    }
}
