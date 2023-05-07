package hr.algebra.webshop.service;

import hr.algebra.webshop.model.PurchasedCart;
import hr.algebra.webshop.repository.PurchasedCartRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchasedCartService {
    private final PurchasedCartRepository purchasedCartRepository;

    public PurchasedCartService(PurchasedCartRepository purchasedCartRepository) {
        this.purchasedCartRepository = purchasedCartRepository;
    }

    public void addPurchasedCartItem(PurchasedCart purchasedCartItem) {
        purchasedCartRepository.save(purchasedCartItem);
    }
}
