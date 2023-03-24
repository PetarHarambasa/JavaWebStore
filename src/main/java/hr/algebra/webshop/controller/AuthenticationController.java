package hr.algebra.webshop.controller;

import hr.algebra.webshop.model.ShopUser;
import hr.algebra.webshop.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class AuthenticationController {
    @Autowired
    private UserService userService;
    private List<ShopUser> shopUserList;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @SneakyThrows
    @PostMapping("/login")
    public String processLoginForm(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        shopUserList = userService.getAllUsers();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (shopUserList.isEmpty()) {
            model.addAttribute("ErrorMessage", "Login failed! Wrong credentials!");
        } else {

            for (ShopUser searchShopUser : shopUserList) {
                if (searchShopUser.getEmail().trim().equals(email.trim()) && passwordEncoder.matches(password.trim(), searchShopUser.getPassword().trim())) {
                    TimeUnit.SECONDS.sleep(3);
                    return "redirect:/dragonBallStore";
                } else {
                    model.addAttribute("ErrorMessage", "Login failed! Wrong credentials!");
                    model.addAttribute("Message", "");
                }
            }
        }
        return "login";
    }
}
