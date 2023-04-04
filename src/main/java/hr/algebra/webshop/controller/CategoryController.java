package hr.algebra.webshop.controller;

import hr.algebra.webshop.service.CategoryService;
import hr.algebra.webshop.service.MerchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static hr.algebra.webshop.controller.AuthenticationController.authenticated;

@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private final MerchService merchService;

    public CategoryController(CategoryService categoryService, MerchService merchService) {
        this.categoryService = categoryService;
        this.merchService = merchService;
    }

    @GetMapping("/dragonBallCategories")
    public String getCategories(Model model) {
        model.addAttribute("CheckAuth", authenticated);
        model.addAttribute("CategoryList", categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/dragonBallCategories/category/{id}")
    public String getSingleProduct(@PathVariable Long id, Model model) {
        model.addAttribute("CheckAuth", authenticated);
        if (merchService.getMerchesByCategoryId(id).isEmpty()) {
            model.addAttribute("MerchByCategoryList", "Category with id " + id + " doesn't exist or doesn't have any products");
            return "productsByCategoryNotFound";
        }
        model.addAttribute("MerchByCategoryList", merchService.getMerchesByCategoryId(id));
        return "productsByCategory";
    }
}
