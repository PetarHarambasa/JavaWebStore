package hr.algebra.webshop.repository;

import hr.algebra.webshop.model.PurchasedBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasedBillRepository extends JpaRepository<PurchasedBill, Long> {
    PurchasedBill findByIdPurchaseBill(Long id);
    List<PurchasedBill> findPurchasedBillByIdPurchaseBill(Long id);
}
