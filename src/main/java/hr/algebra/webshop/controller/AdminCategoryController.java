package hr.algebra.webshop.controller;


import hr.algebra.webshop.model.Category;
import hr.algebra.webshop.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

import static hr.algebra.webshop.controller.AuthenticationController.authenticatedShopUser;
import static hr.algebra.webshop.utils.ImageUtils.imageConvertToBase64ForCategory;

@Controller
public class AdminCategoryController {
    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/adminCategory")
    public String getAdminCategory(Model model) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        if (authenticatedShopUser.isAuthenticated()) {
            model.addAttribute("categoryList", categoryService.getAllCategories());
            return "adminCategory";
        }
        return "redirect:/dragonBallStore";
    }

    @GetMapping("/adminCategory/createNewCategory")
    public String createNewCategory(Model model) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        model.addAttribute("newCategory", new Category());
        return "createNewCategory";
    }

    @PostMapping("/adminCategory/createNewCategory")
    public String addCreatedNewMerch(Model model,
                                     @ModelAttribute("newCategory") Category newCategory, @RequestParam("imageBase64") MultipartFile imageBase64) throws IOException {
        imageConvertToBase64ForCategory(newCategory, imageBase64);
        categoryService.addCategory(newCategory);
        model.addAttribute("Message", "Category created successfully");
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        return "createNewCategory";
    }

    @GetMapping("/adminCategory/deleteCategory/{idCategory}")
    public String deleteCategory(@PathVariable Long idCategory, Model model) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        if (idCategory == null) {
            return "redirect:/adminCategory";
        }
        categoryService.deleteCategoryById(idCategory);
        return "redirect:/adminCategory";
    }

    @GetMapping("/adminCategory/editCategory/{idCategory}")
    public String editCategory(@PathVariable Long idCategory, Model model) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        Category category = categoryService.getSingleCategory(idCategory);
        if (category == null) {
            model.addAttribute("Category", "Category with id " + idCategory + " not found!");
            return "notFound";
        }
        model.addAttribute("editCategory", category);
        return "editCategory";
    }

    @PostMapping("/adminCategory/editCategory/{idCategory}")
    public String editCategory(@ModelAttribute("editCategory") Category editCategory,
                            @RequestParam("imageBase64") MultipartFile imageBase64,
                               Model model) throws IOException {
        Category currentEditedCategory = categoryService.getSingleCategory(editCategory.getIdCategory());
        if (!Objects.equals(imageBase64.getOriginalFilename(), "")) {
            imageConvertToBase64ForCategory(editCategory, imageBase64);
        } else {
            editCategory.setFrontImageBase64(currentEditedCategory.getFrontImageBase64());
        }
        categoryService.addCategory(editCategory);
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        return "redirect:/adminCategory";
    }
}
