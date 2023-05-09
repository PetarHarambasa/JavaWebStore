package hr.algebra.webshop.repository;

import hr.algebra.webshop.model.MerchCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchCartRepository extends JpaRepository<MerchCart, Long> {
    List<MerchCart> getAllByShopUserId(Long id);

    MerchCart getMerchCartByIdMerchCart(Long id);

}
