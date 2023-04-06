package hr.algebra.webshop.controller;

import hr.algebra.webshop.model.Merch;
import hr.algebra.webshop.service.CategoryService;
import hr.algebra.webshop.service.MerchService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

import static hr.algebra.webshop.controller.AuthenticationController.isAuthenticated;

@Controller
public class AdminMerchController {

    private final MerchService merchService;
    private final CategoryService categoryService;

    public AdminMerchController(MerchService merchService, CategoryService categoryService) {
        this.merchService = merchService;
        this.categoryService = categoryService;
    }

    @GetMapping("/adminMerch")
    public String getAdminMerch(Model model){
        if (isAuthenticated){
            model.addAttribute("merchList", merchService.getAllMerchItems());
            return "adminMerch";
        }
        return "redirect:/dragonBallStore";
    }

    public String getCategoryName(Long id){
        return categoryService.findCategoryByIdCategory(id).getName();
    }

    @PostMapping("/adminMerch")
    public String createNewMerch(Model model, @ModelAttribute("newMerch") Merch merch){
        return  "adminMerch";
    }
}
