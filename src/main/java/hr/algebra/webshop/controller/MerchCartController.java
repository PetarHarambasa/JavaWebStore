package hr.algebra.webshop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.algebra.webshop.model.MerchCart;
import hr.algebra.webshop.service.MerchCartService;
import hr.algebra.webshop.service.MerchService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static hr.algebra.webshop.controller.AuthenticationController.authenticatedShopUser;
import static hr.algebra.webshop.controller.DragonBallMerchController.merchCartItems;

@Controller
public class MerchCartController {

    private final MerchService merchService;
    private final MerchCartService merchCartService;

    ObjectMapper objectMapper = new ObjectMapper();

    public MerchCartController(MerchService merchService, MerchCartService merchCartService) {
        this.merchService = merchService;
        this.merchCartService = merchCartService;
    }

    @SneakyThrows
    @GetMapping("/merchCart")
    public String getProducts(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);

        if (authenticatedShopUser.getIdShopUser() == null) {
            getItemsFromCookies(request);
            model.addAttribute("CartMerchList", merchCartItems);
        } else {
            getItemsFromCookies(request);
            clearCookies(request, response);
            for (MerchCart merchCart : merchCartItems) {
                merchCart.setShopUserId(authenticatedShopUser.getIdShopUser());
                merchCartService.addMerchCart(merchCart);
            }
            merchCartItems = merchCartService.getAllMerchCartForUser(authenticatedShopUser.getIdShopUser());
            for (MerchCart updatedMerchCart : merchCartItems) {
                updatedMerchCart.setMerchIdInCart(updatedMerchCart.getIdMerchCart());
            }
            model.addAttribute("CartMerchList", merchCartItems);
        }

        return "merchCart";
    }

    private void clearCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    @SneakyThrows
    private List<MerchCart> getItemsFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cart_items")) {
                    String cartItemsJson = cookie.getValue();
                    ObjectMapper objectMapper = new ObjectMapper();
                    String decodedCartItems = URLDecoder.decode(cartItemsJson, StandardCharsets.UTF_8);
                    merchCartItems = objectMapper.readValue(decodedCartItems,
                            new TypeReference<>() {
                            });
                }
            }
        }
        return merchCartItems;
    }

    public String getMerchName(Long id) {
        return merchService.getSingleMerch(id).getDescription();
    }

    @SneakyThrows
    @GetMapping("/merchCart/removeFromCart/{merchIdInCart}")
    public String removeFormCart(@PathVariable Long merchIdInCart,
                                 HttpServletRequest request,
                                 Model model, HttpServletResponse response) {

        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        if (authenticatedShopUser.getIdShopUser() == null) {

            merchCartItems = getItemsFromCookies(request);

            merchCartItems.removeIf(cartItem -> cartItem.getMerchIdInCart().equals(merchIdInCart));

            String updatedCartItemsJson = objectMapper.writeValueAsString(merchCartItems);
            String encodedCartItems = URLEncoder.encode(updatedCartItemsJson, StandardCharsets.UTF_8);

            setCartCookieValue(response, encodedCartItems);
        } else {
            merchCartService.deleteMerchById(merchIdInCart);
            merchCartItems = merchCartService.getAllMerchCartForUser(authenticatedShopUser.getIdShopUser());
        }

        model.addAttribute("CartMerchList", merchCartItems);

        return "redirect:/merchCart";
    }

    private void setCartCookieValue(HttpServletResponse response, String encodedCartItems) {
        Cookie cartCookie = new Cookie("cart_items", encodedCartItems);

        cartCookie.setMaxAge(24 * 60 * 60); // 1 day
        cartCookie.setPath("/");

        response.addCookie(cartCookie);
    }

    @GetMapping("/merchCart/checkout")
    public String goToCheckOut(Model model) {
        if (authenticatedShopUser.getIdShopUser() == null) {
            return "redirect:/login";
        }
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);

        return "checkout";
    }

    @GetMapping("/merchCart/editAmountOfItemInCart/{merchIdInCart}")
    public String changeAmountOnItemInCart(@PathVariable Long merchIdInCart,
                                           Model model) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);

        MerchCart editMerchCart = merchCartItems.stream().filter(merchCart ->
                        Objects.equals(merchCart.getMerchIdInCart(), merchIdInCart))
                .findFirst().orElse(null);
        if (editMerchCart == null) {
            model.addAttribute("Product", "Product with id " + merchIdInCart + " " +
                    "was not found in merch cart!");
            return "notFound";
        }
        model.addAttribute("Product", merchService.getSingleMerch(editMerchCart.getMerchId()));
        model.addAttribute("ProductInCart", editMerchCart);

        return "editAmountItemInCart";

    }

    @SneakyThrows
    @PostMapping("/merchCart/editAmountOfItemInCart/{merchIdInCart}")
    public String saveEditedItemToCart(@PathVariable Long merchIdInCart, Model model,
                                       @RequestParam("amount") int amount,
                                       HttpServletResponse response) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);

        MerchCart editMerchCart;
        if (authenticatedShopUser.getIdShopUser() == null) {
            editMerchCart = merchCartItems.stream().filter(merchCart ->
                            Objects.equals(merchCart.getMerchIdInCart(), merchIdInCart))
                    .findFirst().orElse(null);
            if (editMerchCart == null) {
                model.addAttribute("Product", "Product with id " + merchIdInCart + " " +
                        "was not found in merch cart!");
                return "notFound";
            }

            Objects.requireNonNull(merchCartItems.stream().filter(merchCart ->
                            Objects.equals(merchCart.getMerchIdInCart(), merchIdInCart))
                    .findFirst().orElse(null)).setAmount(amount);

            String updatedCartItemsJson = objectMapper.writeValueAsString(merchCartItems);
            String encodedCartItems = URLEncoder.encode(updatedCartItemsJson, StandardCharsets.UTF_8);

            setCartCookieValue(response, encodedCartItems);

        } else {
            editMerchCart = merchCartItems.stream().filter(merchCart ->
                            Objects.equals(merchCart.getMerchIdInCart(), merchIdInCart))
                    .findFirst().orElse(null);
            Objects.requireNonNull(merchCartItems.stream().filter(merchCart ->
                            Objects.equals(merchCart.getMerchIdInCart(), merchIdInCart))
                    .findFirst().orElse(null)).setAmount(amount);
            merchCartService.addMerchCart(editMerchCart);
            if (editMerchCart == null) {
                model.addAttribute("Product", "Product with id " + merchIdInCart + " " +
                        "was not found in merch cart!");
                return "notFound";
            }

        }
        model.addAttribute("CartMerchList", merchCartItems);

        return "redirect:/merchCart";
    }
}
