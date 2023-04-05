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

import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class AuthenticationController {
    private final UserService userService;
    public static ShopUser currentShopUser = new ShopUser();
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(Model model)
    {
        model.addAttribute("CheckAuth", currentShopUser.isAuthenticated());
        model.addAttribute("UserRole", currentShopUser.getUserRoleId());
        if (currentShopUser.isAuthenticated()){
            return "redirect:/dragonBallStore";
        }
        return "login";
    }

    @SneakyThrows
    @PostMapping("/login")
    public String processLoginForm(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        List<ShopUser> shopUserList = userService.getAllUsers();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (shopUserList.isEmpty()) {
            model.addAttribute("ErrorMessage", "Login failed! Wrong credentials!");
        } else {
            for (ShopUser searchShopUser : shopUserList) {
                if (searchShopUser.getEmail().trim().equals(email.trim()) && passwordEncoder.matches(password.trim(), searchShopUser.getPassword().trim())) {
                    currentShopUser = userService.getUserById(searchShopUser.getIdShopUser());
                    model.addAttribute("CheckAuth", currentShopUser.isAuthenticated());
                    currentShopUser.setAuthenticated(true);
                    TimeUnit.SECONDS.sleep(3);
                    return "redirect:/dragonBallStore";
                } else {
                    model.addAttribute("CheckAuth", currentShopUser.isAuthenticated());
                    model.addAttribute("ErrorMessage", "Login failed! Wrong credentials!");
                    model.addAttribute("Message", "");
                }
            }
        }
        return "login";
    }

    @SneakyThrows
    @GetMapping("/logout")
    public String logoutProcess(Model model)
    {
        model.addAttribute("CheckAuth", currentShopUser.isAuthenticated());
        if (!currentShopUser.isAuthenticated())
        {
            return "redirect:/login";
        }
        currentShopUser.setAuthenticated(false);
        TimeUnit.SECONDS.sleep(2);
        model.addAttribute("CheckAuth", currentShopUser.isAuthenticated());
        return "login";
    }
}
