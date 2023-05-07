package hr.algebra.webshop.repository;

import hr.algebra.webshop.model.PurchasedCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedCartRepository extends JpaRepository<PurchasedCart, Long> {
}
