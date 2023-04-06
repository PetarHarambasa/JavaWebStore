package hr.algebra.webshop.repository;

import hr.algebra.webshop.model.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<ShopUser, Long> {
    ShopUser findByEmailContainingAndPasswordContaining(String email, String password);
    ShopUser findShopUserByIdShopUser(Long id);

    ShopUser findByEmailContaining (String email);

}
