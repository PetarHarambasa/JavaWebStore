package hr.algebra.webshop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    public PurchasedBill(Long purchaseTypeId) {
        this.purchaseTypeId = purchaseTypeId;
    }

    public PurchasedBill(Timestamp dateOfBuying, Long purchaseTypeId, BigDecimal totalPrice) {
        this.dateOfBuying = dateOfBuying;
        this.purchaseTypeId = purchaseTypeId;
        this.totalPrice = totalPrice;
    }
}
