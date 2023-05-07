package hr.algebra.webshop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchased_cart")
@Data
@NoArgsConstructor
public class PurchasedCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_purchased_cart")
    private Long idPurchasedCart;

    @Column(name = "merch_id", nullable = false)
    @JoinColumn(referencedColumnName = "id_merch")
    private Long merchId;

    @Column(name = "shop_user_id", nullable = false)
    @JoinColumn(referencedColumnName = "id_shop_user")
    private Long shopUserId;

    @Column(name = "purchased_bill_id", nullable = false)
    @JoinColumn(referencedColumnName = "id_purchased_bill")
    private Long purchasedBillId;

    @Column(name = "amount", nullable = false)
    private int amount;

    public PurchasedCart(Long merchId, Long shopUserId, Long purchasedBillId, int amount) {
        this.merchId = merchId;
        this.shopUserId = shopUserId;
        this.purchasedBillId = purchasedBillId;
        this.amount = amount;
    }
}
