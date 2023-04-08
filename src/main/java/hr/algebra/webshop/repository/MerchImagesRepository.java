package hr.algebra.webshop.repository;

import hr.algebra.webshop.model.MerchImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchImagesRepository extends JpaRepository<MerchImages, Long> {
    public void deleteMerchImagesByMerchId(Long id);
}
