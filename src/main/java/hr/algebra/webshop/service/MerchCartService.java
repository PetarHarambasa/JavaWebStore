package hr.algebra.webshop.service;

import hr.algebra.webshop.model.Category;
import hr.algebra.webshop.model.MerchCart;
import hr.algebra.webshop.repository.MerchCartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchCartService {

    private final MerchCartRepository merchCartRepository;

    public MerchCartService(MerchCartRepository merchCartRepository) {
        this.merchCartRepository = merchCartRepository;
    }

    public void addMerchCart(MerchCart merchCart){
        merchCartRepository.save(merchCart);
    }

    public List<MerchCart> getAllMerchCartForUser(Long id){
       return merchCartRepository.getAllByShopUserId(id);
    }

    public void deleteMerchById(Long id){
        merchCartRepository.deleteById(id);
    }

    public MerchCart getSingleMerch(Long id){
        return merchCartRepository.getMerchCartByIdMerchCart(id);
    }
}
