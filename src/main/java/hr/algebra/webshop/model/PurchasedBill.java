package hr.algebra.webshop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "purchased_bill")
@Data
@NoArgsConstructor
public class PurchasedBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_purchase_bill")
    private Long idPurchaseBill;

    @Column(name = "date_of_buying", nullable = false)
    private Timestamp dateOfBuying;

    @Column(name = "purchase_type_id", nullable = false)
    @JoinColumn(referencedColumnName = "id_purchase_type")
    private Long purchaseTypeId;

    public PurchasedBill(Long purchaseTypeId) {
        this.purchaseTypeId = purchaseTypeId;
    }

    public PurchasedBill(Timestamp dateOfBuying, Long purchaseTypeId) {
        this.dateOfBuying = dateOfBuying;
        this.purchaseTypeId = purchaseTypeId;
    }
}
