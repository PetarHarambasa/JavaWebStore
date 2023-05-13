package hr.algebra.webshop.service;

import hr.algebra.webshop.model.LoginHistory;
import hr.algebra.webshop.repository.LoginHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginHistoryService {
    private final LoginHistoryRepository loginHistoryRepository;

    public LoginHistoryService(LoginHistoryRepository loginHistoryRepository) {
        this.loginHistoryRepository = loginHistoryRepository;
    }

    public void saveLogin(LoginHistory loginHistory) {
        loginHistoryRepository.save(loginHistory);
    }

    public List<LoginHistory> getAllLoginHistoryShopUsers() {
        return loginHistoryRepository.findAll();
    }
}
