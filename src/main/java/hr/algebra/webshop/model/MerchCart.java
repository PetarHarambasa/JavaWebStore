package hr.algebra.webshop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="merch_cart")
@Data
@NoArgsConstructor
public class MerchCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_merch")
    private Long idMerchCart;

    @Column(name = "merch_id", nullable = false)
    @JoinColumn(referencedColumnName = "id_merch")
    private Long merchId;

    @Column(name = "amount", nullable = false)
    private int amount;

    public MerchCart(Long merchId, int amount) {
        this.merchId = merchId;
        this.amount = amount;
    }
}
