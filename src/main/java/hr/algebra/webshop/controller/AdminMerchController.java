package hr.algebra.webshop.controller;

import hr.algebra.webshop.model.Category;
import hr.algebra.webshop.model.Merch;
import hr.algebra.webshop.service.CategoryService;
import hr.algebra.webshop.service.MerchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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

    @GetMapping("/adminMerch/createNewMerch")
    public String createNewMerch(){
        return "createNewMerch";
    }

    @PostMapping("/adminMerch/createNewMerch")
    public String addCreatedNewMerch(Model model, @ModelAttribute("newMerch") Merch newMerch){
        byte[] imageData = newMerch.getFrontImageBase64().getBytes();
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        newMerch.setFrontImageBase64(base64Image);
        merchService.addNewMerch(newMerch);
        model.addAttribute("Message", "Merch created successfully");
        return  "createNewMerch";
    }

    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }
}
