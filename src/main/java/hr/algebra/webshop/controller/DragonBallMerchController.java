package hr.algebra.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DragonBallMerchController {
    @GetMapping("/dragonBallStore")
    public String getItems(){
        return "dragonBallItems";
    }
}
