package hr.algebra.webshop.controller;

import hr.algebra.webshop.model.ShopUser;
import hr.algebra.webshop.model.UserRole;
import hr.algebra.webshop.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    private List<ShopUser> shopUserList;

    @SneakyThrows
    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute("newUser") ShopUser shopUser, @ModelAttribute("role") UserRole role, Model model) {
        if (role.name().equals("ADMIN")){
            shopUser.setUserRoleId(1L);
        } else if (role.name().equals("CUSTOMER")) {
            shopUser.setUserRoleId(2L);
        }
        shopUserList = userService.getAllUsers();
        if (shopUserList.isEmpty()){
            userService.saveUser(shopUser);
            model.addAttribute("Message", "Register successful!");
            model.addAttribute("ErrorMessage", "");
        }
        for (ShopUser searchShopUser : shopUserList){
            if (searchShopUser.getEmail().equals(shopUser.getEmail())){
                model.addAttribute("ErrorMessage", "Register failed! Email already exists!");
                model.addAttribute("Message", "");
            }else{
                userService.saveUser(shopUser);
                model.addAttribute("Message", "Register successful!");
                model.addAttribute("ErrorMessage", "");
            }
        }
        return "register";
    }
    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("newUser", new ShopUser());
        return "register";
    }
}
