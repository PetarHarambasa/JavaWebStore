package hr.algebra.webshop.repository;

import hr.algebra.webshop.model.PurchasedCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasedCartRepository extends JpaRepository<PurchasedCart, Long> {
    List<PurchasedCart> findAllByShopUserId(Long id);
}
