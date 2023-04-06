package hr.algebra.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static hr.algebra.webshop.controller.AuthenticationController.isAuthenticated;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("CheckAuth", isAuthenticated);
        return "index";
    }
}
