package hr.algebra.webshop.service;

import hr.algebra.webshop.model.PurchasedCart;
import hr.algebra.webshop.repository.PurchasedCartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchasedCartService {
    private final PurchasedCartRepository purchasedCartRepository;

    public PurchasedCartService(PurchasedCartRepository purchasedCartRepository) {
        this.purchasedCartRepository = purchasedCartRepository;
    }

    public void addPurchasedCartItem(PurchasedCart purchasedCartItem) {
        purchasedCartRepository.save(purchasedCartItem);
    }

    public List<PurchasedCart> getPurchasedCartListByShopUserId(Long id) {
        return purchasedCartRepository.findAllByShopUserId(id);
    }
}
