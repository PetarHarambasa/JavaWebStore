package hr.algebra.webshop.controller;

import hr.algebra.webshop.model.ShopUser;
import hr.algebra.webshop.service.UserService;
import lombok.SneakyThrows;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

@Controller
public class AuthenticationController {
    public static ShopUser authenticatedShopUser = new ShopUser(false);
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        if (authenticatedShopUser.isAuthenticated()) {
            return "redirect:/dragonBallStore";
        }
        return "login";
    }

    @SneakyThrows
    @PostMapping("/login")
    public String processLoginForm(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ShopUser shopUser = userService.getUserByEmail(email);
        if (shopUser == null) {
            model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
            model.addAttribute("ErrorMessage", "Login failed! Wrong credentials!");
        } else {
            if (shopUser.getEmail().trim().equals(email.trim()) && passwordEncoder.matches(password.trim(), shopUser.getPassword().trim())) {
                model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
                authenticatedShopUser = shopUser;
                authenticatedShopUser.setAuthenticated(true);
                userService.saveUser(authenticatedShopUser);
                TimeUnit.SECONDS.sleep(1);
                return "redirect:/dragonBallStore";
            } else {
                model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
                model.addAttribute("ErrorMessage", "Login failed! Wrong credentials!");
                model.addAttribute("Message", "");
            }
        }
        return "login";
    }

    @SneakyThrows
    @GetMapping("/logout")
    public String logoutProcess(Model model) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        if (!authenticatedShopUser.isAuthenticated()) {
            return "redirect:/login";
        }
        authenticatedShopUser.setAuthenticated(false);
        userService.saveUser(authenticatedShopUser);
        TimeUnit.SECONDS.sleep(1);
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        return "login";
    }
}
