package hr.algebra.webshop.controller;

import hr.algebra.webshop.model.MerchCart;
import hr.algebra.webshop.model.PurchasedBill;
import hr.algebra.webshop.model.PurchasedCart;
import hr.algebra.webshop.model.PurchasedType;
import hr.algebra.webshop.service.MerchCartService;
import hr.algebra.webshop.service.PurchasedBillService;
import hr.algebra.webshop.service.PurchasedCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hr.algebra.webshop.controller.AuthenticationController.authenticatedShopUser;
import static hr.algebra.webshop.controller.DragonBallMerchController.merchCartItems;

@Controller
public class UserController {

    private final PurchasedCartService purchasedCartService;
    private final PurchasedBillService purchasedBillService;
    private final MerchCartService merchCartService;

    public UserController(PurchasedCartService purchasedCartService, PurchasedBillService purchasedBillService, MerchCartService merchCartService) {
        this.purchasedCartService = purchasedCartService;
        this.purchasedBillService = purchasedBillService;
        this.merchCartService = merchCartService;
    }

    @GetMapping("userHistory")
    public String getUserHistory(Model model) {
        if (authenticatedShopUser.isAuthenticated()) {
            model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);

            Map<Long, List<PurchasedCart>> mapOfPurchasedCartList = new HashMap<>();
            List<PurchasedCart> purchasedCartList = purchasedCartService.getPurchasedCartListByShopUserId
                    (authenticatedShopUser.getIdShopUser());
            for (PurchasedCart purchasedCart : purchasedCartList) {
                List<PurchasedCart> purchasedCartListForMap = mapOfPurchasedCartList.get(purchasedCart.getPurchasedBillId());
                if (purchasedCartListForMap == null) {
                    purchasedCartListForMap = new ArrayList<>();
                    purchasedCartListForMap.add(purchasedCart);
                }else{
                    purchasedCartListForMap.add(purchasedCart);
                }
                mapOfPurchasedCartList.put(purchasedCart.getPurchasedBillId(), purchasedCartListForMap);
            }

            model.addAttribute("MapOfPurchasedCartList", mapOfPurchasedCartList);
            return "userHistory";
        }
        return "redirect:/dragonBallStore";
    }

    @PostMapping("userHistory")
    public String savePurchasedCart(@RequestParam("typeOfPurchase") String typeOfPurchase,
                                    @RequestParam("totalPrice") String totalPrice, Model model) {

        if (authenticatedShopUser.isAuthenticated()) {
            model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
            if (typeOfPurchase.equals("CASH")) {
                if (!merchCartItems.isEmpty()) {

                    PurchasedBill purchasedBillCash = new PurchasedBill
                            (Timestamp.valueOf(LocalDateTime.now()), 2L, new BigDecimal(totalPrice));
                    purchasedBillService.addPurchasedBill(purchasedBillCash);

                    processPurchasedCart(purchasedBillCash);
                }
            } else if (typeOfPurchase.equals("PAYPAL")) {
                if (!merchCartItems.isEmpty()) {
                    PurchasedBill purchasedBillPayPal = new PurchasedBill
                            (Timestamp.valueOf(LocalDateTime.now()), 1L, new BigDecimal(totalPrice));
                    purchasedBillService.addPurchasedBill(purchasedBillPayPal);

                    processPurchasedCart(purchasedBillPayPal);
                }

            }
            return "redirect:/userHistory";
        }
        return "redirect:/dragonBallStore";
    }

    private void processPurchasedCart(PurchasedBill purchasedBillPayPal) {
        for (MerchCart merchCart : merchCartItems) {
            PurchasedCart purchasedCartItem = new PurchasedCart(merchCart.getMerchId(),
                    merchCart.getShopUserId(), purchasedBillPayPal.getIdPurchaseBill(), merchCart.getAmount());
            purchasedCartService.addPurchasedCartItem(purchasedCartItem);
            merchCartService.deleteMerchById(merchCart.getIdMerchCart());
        }
        merchCartItems = merchCartService.getAllMerchCartForUser(authenticatedShopUser.getIdShopUser());
    }

    public PurchasedBill getPurchasedBill(Long id) {
        return purchasedBillService.getPurchasedBillById(id);
    }

    public PurchasedType getPurchaseTypeName(Long id) {
        if (id == 1) {
            return PurchasedType.PAYPAL;
        } else if (id == 2) {
            return PurchasedType.CASH;
        }
        return null;
    }

    public String formatDateTimeForBill(Timestamp dateOfPurchase){
        int dotIndex = dateOfPurchase.toString().indexOf(".");
        return dateOfPurchase.toString().substring(0, dotIndex);
    }
}
