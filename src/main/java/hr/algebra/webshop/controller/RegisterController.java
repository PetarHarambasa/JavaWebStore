package hr.algebra.webshop.controller;

import hr.algebra.webshop.model.ShopUser;
import hr.algebra.webshop.model.UserRole;
import hr.algebra.webshop.service.UserService;
import lombok.SneakyThrows;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static hr.algebra.webshop.controller.AuthenticationController.authenticatedShopUser;

@Controller
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        if (authenticatedShopUser.isAuthenticated()) {
            return "redirect:/dragonBallStore";
        }
        model.addAttribute("newUser", new ShopUser());
        return "register";
    }

    @SneakyThrows
    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute("newUser") ShopUser shopUser,
                                          @ModelAttribute("role") UserRole role, Model model) {
        if (role.name().equals("ADMIN")) {
            shopUser.setUserRoleId(1L);
        } else if (role.name().equals("CUSTOMER")) {
            shopUser.setUserRoleId(2L);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(shopUser.getPassword());
        shopUser.setPassword(encodedPassword);

        ShopUser newShopUser = userService.getUserByEmail(shopUser.getEmail().trim());
        if (newShopUser == null) {
            userSaving(shopUser, model);
        } else {
            if (newShopUser.getEmail().trim().equals(shopUser.getEmail().trim())) {
                model.addAttribute("ErrorMessage", "Register failed! Email already exists!");
                model.addAttribute("Message", "");
            } else {
                userSaving(shopUser, model);
            }
        }
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        return "register";
    }

    @SneakyThrows
    private void userSaving(ShopUser shopUser, Model model) {
        userService.saveUser(shopUser);
        model.addAttribute("Message", "Register successful!");
        model.addAttribute("ErrorMessage", "");
    }
}
