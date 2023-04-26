package hr.algebra.webshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.algebra.webshop.model.Merch;
import hr.algebra.webshop.model.MerchCart;
import hr.algebra.webshop.service.MerchService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static hr.algebra.webshop.controller.AuthenticationController.authenticatedShopUser;

@Controller
public class DragonBallMerchController {

    private final MerchService merchService;

    public DragonBallMerchController(MerchService merchService) {
        this.merchService = merchService;
    }

    List<MerchCart> merchCartItems = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/dragonBallStore")
    public String getProducts(Model model) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        model.addAttribute("MerchList", merchService.getAllMerchItems());
        return "dragonBallItems";
    }

    @GetMapping("/dragonBallStore/product/{id}")
    public String getSingleProduct(@PathVariable Long id, Model model) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        Merch merch = merchService.getSingleMerch(id);
        if (merch == null) {
            model.addAttribute("Product", "Product with id " + id + " not found!");
            return "notFound";
        }
        model.addAttribute("Product", merch);
        return "product";
    }

    @SneakyThrows
    @PostMapping("/dragonBallStore/product/{id}")
    public String saveSingleProductToCart(@RequestParam("amount") int amount,
                                                      @RequestParam("merchId") Long merchId,
                                                      HttpServletResponse response, Model model) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        if(authenticatedShopUser.getIdShopUser() == null){
            MerchCart merchCart = new MerchCart(merchId, amount);
            merchCartItems.add(merchCart);

            String cartItemsJson = objectMapper.writeValueAsString(merchCartItems);
            String encodedCartItems = URLEncoder.encode(cartItemsJson, StandardCharsets.UTF_8);
            Cookie cartCookie = new Cookie("cart_items", encodedCartItems);
            cartCookie.setMaxAge(24 * 60 * 60); // 1 day
            cartCookie.setPath("/");

            response.addCookie(cartCookie);
        }

        return "redirect:/merchCart";
    }
}
