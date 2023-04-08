package hr.algebra.webshop.service;

import hr.algebra.webshop.model.Merch;
import hr.algebra.webshop.repository.MerchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchService{

    private final MerchRepository merchRepository;

    private final MerchImagesService merchImagesService;

    public MerchService(MerchRepository merchRepository, MerchImagesService merchImagesService) {
        this.merchRepository = merchRepository;
        this.merchImagesService = merchImagesService;
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

    public void deleteMerchById(Long id){
        merchImagesService.deleteImages(id);
        merchRepository.deleteById(id);
    }
}
