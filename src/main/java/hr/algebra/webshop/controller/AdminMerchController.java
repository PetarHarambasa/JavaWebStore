package hr.algebra.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static hr.algebra.webshop.controller.AuthenticationController.currentShopUser;

@Controller
public class AdminMerchController {

    @GetMapping("/adminMerch")
    public String getAdminMerch(Model model){
        if (currentShopUser.getUserRoleId() == 1){
            return "adminMerch";
        }
        return "redirect:/dragonBallStore";
    }
}
