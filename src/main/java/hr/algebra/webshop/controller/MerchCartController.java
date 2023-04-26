package hr.algebra.webshop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.algebra.webshop.model.MerchCart;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static hr.algebra.webshop.controller.AuthenticationController.authenticatedShopUser;

@Controller
public class MerchCartController {

    List<MerchCart> merchCartItems = new ArrayList<>();

    @SneakyThrows
    @GetMapping("/merchCart")
    public String getProducts(Model model, HttpServletRequest request) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);

        if(authenticatedShopUser.getIdShopUser() == null){
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("cart_items")) {
                        String cartItemsJson = cookie.getValue();
                        ObjectMapper objectMapper = new ObjectMapper();
                        String decodedCartItems = URLDecoder.decode(cartItemsJson, StandardCharsets.UTF_8);
                        merchCartItems = objectMapper.readValue(decodedCartItems,
                                new TypeReference<>() {});
                        break;
                    }
                }
            }
        }

        model.addAttribute("CartMerchList", merchCartItems);

        return "merchCart";
    }
}
