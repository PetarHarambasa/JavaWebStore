package hr.algebra.webshop.service;

import hr.algebra.webshop.model.MerchImages;
import hr.algebra.webshop.repository.MerchImagesRepository;
import org.springframework.stereotype.Service;

@Service
public class MerchImagesService {
    private final MerchImagesRepository merchImagesRepository;

    public MerchImagesService(MerchImagesRepository merchImagesRepository) {
        this.merchImagesRepository = merchImagesRepository;
    }

    public void deleteImages(Long id){
        merchImagesRepository.deleteMerchImagesByMerchId(id);
    }
}
