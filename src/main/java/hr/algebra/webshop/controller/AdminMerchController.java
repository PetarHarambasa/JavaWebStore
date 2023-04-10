package hr.algebra.webshop.controller;

import hr.algebra.webshop.model.Category;
import hr.algebra.webshop.model.Merch;
import hr.algebra.webshop.service.CategoryService;
import hr.algebra.webshop.service.MerchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

import static hr.algebra.webshop.controller.AuthenticationController.isAuthenticated;
import static hr.algebra.webshop.utils.ImageUtils.imageConvertToBase64ForMerch;

@Controller
public class AdminMerchController {

    private final MerchService merchService;
    private final CategoryService categoryService;

    public AdminMerchController(MerchService merchService, CategoryService categoryService) {
        this.merchService = merchService;
        this.categoryService = categoryService;
    }

    @GetMapping("/adminMerch")
    public String getAdminMerch(Model model) {
        if (isAuthenticated) {
            model.addAttribute("merchList", merchService.getAllMerchItems());
            return "adminMerch";
        }
        return "redirect:/dragonBallStore";
    }

    public String getCategoryName(Long id) {
        return categoryService.findCategoryByIdCategory(id).getName();
    }

    @GetMapping("/adminMerch/createNewMerch")
    public String createNewMerch(Model model) {
        model.addAttribute("newMerch", new Merch());
        return "createNewMerch";
    }

    @PostMapping("/adminMerch/createNewMerch")
    public String addCreatedNewMerch(Model model, @ModelAttribute("newMerch") Merch newMerch, @RequestParam("imageBase64") MultipartFile imageBase64) throws IOException {
        imageConvertToBase64ForMerch(newMerch, imageBase64);
        merchService.addNewMerch(newMerch);
        model.addAttribute("Message", "Merch created successfully");
        return "createNewMerch";
    }

    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/adminMerch/deleteMerch/{idMerch}")
    public String deleteMerch(@PathVariable Long idMerch) {
        if (idMerch == null) {
            return "redirect:/adminMerch";
        }
        merchService.deleteMerchById(idMerch);
        return "redirect:/adminMerch";
    }

    @GetMapping("/adminMerch/editMerch/{idMerch}")
    public String editMerch(@PathVariable Long idMerch, Model model) {
        Merch merch = merchService.getSingleMerch(idMerch);
        if (merch == null) {
            model.addAttribute("Product", "Product with id " + idMerch + " not found!");
            return "notFound";
        }
        model.addAttribute("editMerch", merch);
        return "editMerch";
    }

    @PostMapping("/adminMerch/editMerch/{idMerch}")
    public String editMerch(@ModelAttribute("editMerch") Merch editMerch,
                            @RequestParam("imageBase64") MultipartFile imageBase64) throws IOException {
        Merch currentEditedMerch = merchService.getSingleMerch(editMerch.getIdMerch());
        if (!Objects.equals(imageBase64.getOriginalFilename(), "")) {
            imageConvertToBase64ForMerch(editMerch, imageBase64);
        } else {
            editMerch.setFrontImageBase64(currentEditedMerch.getFrontImageBase64());
        }
        merchService.addNewMerch(editMerch);
        return "redirect:/adminMerch";
    }
}
