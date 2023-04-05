package hr.algebra.webshop.repository;

import hr.algebra.webshop.model.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<ShopUser, Long> {
    List<ShopUser> findByEmailContaining(String email);
    ShopUser findShopUserByIdShopUser(Long id);

}
