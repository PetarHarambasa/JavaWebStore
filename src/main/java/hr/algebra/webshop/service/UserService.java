package hr.algebra.webshop.service;

import hr.algebra.webshop.model.ShopUser;
import hr.algebra.webshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;

    public List<ShopUser> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(ShopUser shopUser) {
        userRepository.save(shopUser);
    }
    public List<ShopUser> searchUsers(String keyword) {
        return userRepository.findByEmailContaining(keyword);
    }
}
