package hr.algebra.webshop.repository;

import hr.algebra.webshop.model.Merch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchRepository extends JpaRepository<Merch, Long> {

}
