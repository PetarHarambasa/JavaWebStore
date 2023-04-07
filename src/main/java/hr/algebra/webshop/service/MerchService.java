package hr.algebra.webshop.service;

import hr.algebra.webshop.model.Merch;
import hr.algebra.webshop.repository.MerchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchService{

    private final MerchRepository merchRepository;

    public MerchService(MerchRepository merchRepository) {
        this.merchRepository = merchRepository;
    }

    public List<Merch> getAllMerchItems() {
        return merchRepository.findAll();
    }

    public Merch getSingleMerch(Long id){
        if (merchRepository.findById(id).isPresent()){
            return merchRepository.findById(id).get();
        }else {
            return null;
        }
    }
    public List<Merch> getMerchByCategoryId(Long id){
        return merchRepository.findMerchesByCategoryId(id);
    }

    public void addNewMerch(Merch newMerch){
        merchRepository.save(newMerch);
    }

}
