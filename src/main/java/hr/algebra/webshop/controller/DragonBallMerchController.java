package hr.algebra.webshop.controller;

import hr.algebra.webshop.service.MerchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static hr.algebra.webshop.controller.AuthenticationController.authenticated;

@Controller
public class DragonBallMerchController {

    private final MerchService merchService;

    public DragonBallMerchController(MerchService merchService) {
        this.merchService = merchService;
    }

    @GetMapping("/dragonBallStore")
    public String getProducts(Model model) {
        model.addAttribute("CheckAuth", authenticated);
        model.addAttribute("MerchList", merchService.getAllMerchItems());
        return "dragonBallItems";
    }

    @GetMapping("/dragonBallStore/product/{id}")
    public String getSingleProduct(@PathVariable Long id, Model model) {
        model.addAttribute("CheckAuth", authenticated);
        if (merchService.getSingleMerch(id) == null) {
            model.addAttribute("Product", "Product with id " + id + " not found!");
            return "productNotFound";
        }
        model.addAttribute("Product", merchService.getSingleMerch(id));
        return "product";
    }
}
