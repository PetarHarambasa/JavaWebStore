package hr.algebra.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static hr.algebra.webshop.controller.AuthenticationController.isAuthenticated;

@Controller
public class UserController {

    @GetMapping("userProfile")
    public String getUserProfileData(Model model){
        if(isAuthenticated){
            return "userProfile";
        }
        return "redirect:dragonBallStore";
    }
}
