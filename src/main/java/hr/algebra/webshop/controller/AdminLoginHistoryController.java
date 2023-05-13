package hr.algebra.webshop.controller;

import hr.algebra.webshop.service.LoginHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static hr.algebra.webshop.controller.AuthenticationController.authenticatedShopUser;

@Controller
public class AdminLoginHistoryController {
    private final LoginHistoryService loginHistoryService;

    public AdminLoginHistoryController(LoginHistoryService loginHistoryService) {
        this.loginHistoryService = loginHistoryService;
    }

    @GetMapping("/loginHistory")
    public String getShopUserLoginHistory(Model model) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        if (authenticatedShopUser.isAuthenticated() && authenticatedShopUser.getUserRoleId() == 1) {
            model.addAttribute("shopUserLoginList", loginHistoryService.getAllLoginHistoryShopUsers());
            return "adminLoginHistory";
        }
        return "redirect:/dragonBallStore";
    }
}
