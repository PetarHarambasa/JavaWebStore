package hr.algebra.webshop.controller;

import hr.algebra.webshop.model.PurchasedBill;
import hr.algebra.webshop.model.PurchasedCart;
import hr.algebra.webshop.model.ShopUser;
import hr.algebra.webshop.service.PurchasedBillService;
import hr.algebra.webshop.service.PurchasedCartService;
import hr.algebra.webshop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hr.algebra.webshop.controller.AuthenticationController.authenticatedShopUser;

@Controller
public class AdminPurchaseHistory {

    private final PurchasedBillService purchasedBillService;
    private final PurchasedCartService purchasedCartService;
    private final UserService userService;

    public AdminPurchaseHistory(PurchasedBillService purchasedBillService,
                                PurchasedCartService purchasedCartService, UserService userService) {
        this.purchasedBillService = purchasedBillService;
        this.purchasedCartService = purchasedCartService;
        this.userService = userService;
    }

    @GetMapping("/shopUserPurchaseHistory")
    public String getAllShopUserPurchaseHistory(Model model) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        if (authenticatedShopUser.isAuthenticated() && authenticatedShopUser.getUserRoleId() == 1) {
            List<ShopUser> listOfAllShopUser = userService.getAllUsers();

            Map<Long, Map<Long, List<PurchasedCart>>> mapOfShopUsersWithMapOfPurchasedCartList = new HashMap<>();

            Map<Long, List<PurchasedCart>> mapOfPurchasedCartList = new HashMap<>();

            for (ShopUser shopUser : listOfAllShopUser) {
                List<PurchasedCart> purchasedCartList = purchasedCartService.
                        getPurchasedCartListByShopUserId(shopUser.getIdShopUser());

                for (PurchasedCart purchasedCart : purchasedCartList) {
                    List<PurchasedCart> purchasedCartListForMap =
                            mapOfPurchasedCartList.get(purchasedCart.getPurchasedBillId());
                    if (purchasedCartListForMap == null) {
                        purchasedCartListForMap = new ArrayList<>();
                        purchasedCartListForMap.add(purchasedCart);
                    } else {
                        purchasedCartListForMap.add(purchasedCart);
                    }

                    mapOfPurchasedCartList.put(purchasedCart.getPurchasedBillId(), purchasedCartListForMap);
                }
                if (!mapOfPurchasedCartList.isEmpty()){
                    mapOfShopUsersWithMapOfPurchasedCartList.put(shopUser.getIdShopUser(),
                            mapOfPurchasedCartList);
                }
                mapOfPurchasedCartList = new HashMap<>();
            }

            model.addAttribute("mapOfShopUsersWithMapOfPurchasedCartList",
                    mapOfShopUsersWithMapOfPurchasedCartList);
            return "adminPurchaseHistory";
        }
        return "redirect:/dragonBallStore";
    }

    public String getShopUserName(Long id){
        return userService.getUserById(id).getUsername();
    }

    public List<PurchasedBill> getPurchasedBillsById(Long id){
        return purchasedBillService.getPurchasedBillsById(id);
    }
}
