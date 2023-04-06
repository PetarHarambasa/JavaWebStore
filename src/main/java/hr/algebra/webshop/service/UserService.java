package hr.algebra.webshop.service;

import hr.algebra.webshop.model.ShopUser;
import hr.algebra.webshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<ShopUser> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(ShopUser shopUser) {
        userRepository.save(shopUser);
    }
    public ShopUser getUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailContainingAndPasswordContaining(email, password);
    }
    public ShopUser getUserById(Long id){
        return userRepository.findShopUserByIdShopUser(id);
    }

    public ShopUser getUserByEmail(String email){
        return userRepository.findByEmailContaining(email);
    }
}
