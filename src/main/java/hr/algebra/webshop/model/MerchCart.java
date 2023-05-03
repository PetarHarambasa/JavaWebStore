package hr.algebra.webshop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "merch_cart")
@Data
@NoArgsConstructor
public class MerchCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_merch")
    private Long idMerchCart;

    @Column(name = "merch_id", nullable = false)
    @JoinColumn(referencedColumnName = "id_merch")
    private Long merchId;

    @Column(name = "shop_user_id", nullable = false)
    @JoinColumn(referencedColumnName = "id_shop_user")
    private Long shopUserId;

    @Column(name = "amount", nullable = false)
    private int amount;

    private Long merchIdInCart;

    public MerchCart(Long merchId, int amount, Long merchIdInCart) {
        this.merchId = merchId;
        this.amount = amount;
        this.merchIdInCart = merchIdInCart;
    }

    public MerchCart(Long merchId, Long shopUserId, int amount) {
        this.merchId = merchId;
        this.shopUserId = shopUserId;
        this.amount = amount;
    }
}
