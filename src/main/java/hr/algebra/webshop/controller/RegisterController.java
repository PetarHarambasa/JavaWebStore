package hr.algebra.webshop.controller;

import hr.algebra.webshop.model.ShopUser;
import hr.algebra.webshop.model.UserRole;
import hr.algebra.webshop.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

import static hr.algebra.webshop.controller.AuthenticationController.authenticated;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("CheckAuth", authenticated);
        if (authenticated){
            return "redirect:/dragonBallStore";
        }
        model.addAttribute("newUser", new ShopUser());
        return "register";
    }

    @SneakyThrows
    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute("newUser") ShopUser shopUser, @ModelAttribute("role") UserRole role, Model model) {
        if (role.name().equals("ADMIN")) {
            shopUser.setUserRoleId(1L);
        } else if (role.name().equals("CUSTOMER")) {
            shopUser.setUserRoleId(2L);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(shopUser.getPassword());
        shopUser.setPassword(encodedPassword);

        List<ShopUser> shopUserList = userService.searchUsers(shopUser.getEmail().trim());
        if (shopUserList.isEmpty()) {
            userSaving(shopUser, model);
        } else {
            for (ShopUser searchShopUser : shopUserList) {
                if (searchShopUser.getEmail().trim().equals(shopUser.getEmail().trim())) {
                    model.addAttribute("ErrorMessage", "Register failed! Email already exists!");
                    model.addAttribute("Message", "");
                } else {
                    userSaving(shopUser, model);
                }
            }
        }
        model.addAttribute("CheckAuth", authenticated);
        return "register";
    }

    @SneakyThrows
    private void userSaving(ShopUser shopUser, Model model) {
        shopUser.setAuthenticated(false);
        userService.saveUser(shopUser);
        model.addAttribute("Message", "Register successful!");
        model.addAttribute("ErrorMessage", "");
    }
}
