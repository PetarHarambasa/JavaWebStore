package hr.algebra.webshop.apis;

import hr.algebra.webshop.model.ShopUser;
import hr.algebra.webshop.service.PurchasedCartService;
import hr.algebra.webshop.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static hr.algebra.webshop.controller.AuthenticationController.authenticatedShopUser;

@RestController
public class AdminPurchaseHistoryApis {

    private final UserService userService;
    private final PurchasedCartService purchasedCartService;

    public AdminPurchaseHistoryApis(UserService userService, PurchasedCartService purchasedCartService) {
        this.userService = userService;
        this.purchasedCartService = purchasedCartService;
    }

    @GetMapping("/getUsersForSelect")
    public List<ShopUser> getUsersForSelect() {
        List<ShopUser> listOfShopUserWithPurchases = new ArrayList<>();
        if (authenticatedShopUser.isAuthenticated() && authenticatedShopUser.getUserRoleId() == 1) {
            for (ShopUser shopUser : userService.getAllUsers())
            if (!(purchasedCartService.getPurchasedCartListByShopUserId(shopUser.getIdShopUser()).isEmpty())){
                listOfShopUserWithPurchases.add(shopUser);
            }
            return listOfShopUserWithPurchases;
        }
        return new ArrayList<>();
    }
}
