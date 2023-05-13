package hr.algebra.webshop.service;

import hr.algebra.webshop.model.PurchasedBill;
import hr.algebra.webshop.repository.PurchasedBillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchasedBillService {
    private final PurchasedBillRepository purchasedBillRepository;

    public PurchasedBillService(PurchasedBillRepository purchasedBillRepository) {
        this.purchasedBillRepository = purchasedBillRepository;
    }

    public void addPurchasedBill(PurchasedBill purchasedBill) {
        purchasedBillRepository.save(purchasedBill);
    }

    public PurchasedBill getPurchasedBillById(Long id){
        return purchasedBillRepository.findByIdPurchaseBill(id);
    }
    public List<PurchasedBill> getPurchasedBillsById(Long id){
        return purchasedBillRepository.findPurchasedBillByIdPurchaseBill(id);
    }
}
