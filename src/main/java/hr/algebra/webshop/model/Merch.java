package hr.algebra.webshop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name="merch")
@Data
public class Merch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_merch")
    private Long idMerch;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "rating", nullable = false)
    private BigDecimal rating;

    @Column(name = "front_image_base64", nullable = false)
    private String frontImageBase64;

    @Column(name = "category_id", nullable = false)
    @JoinColumn(referencedColumnName = "id_category")
    private Long categoryId;
}
