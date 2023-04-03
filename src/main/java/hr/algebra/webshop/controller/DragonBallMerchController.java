package hr.algebra.webshop.controller;

import hr.algebra.webshop.service.MerchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static hr.algebra.webshop.controller.AuthenticationController.authenticated;

@Controller
public class DragonBallMerchController {

    private final MerchService merchService;

    public DragonBallMerchController(MerchService merchService) {
        this.merchService = merchService;
    }

    @GetMapping("/dragonBallStore")
    public String getCategories(Model model){
        model.addAttribute("CheckAuth", authenticated);
        model.addAttribute("MerchList", merchService.getAllMerchItems());
        return "dragonBallItems";
    }
}
