package hr.algebra.webshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="merch_images")
@Data
public class MerchImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_merch_images")
    private Long idMerchImages;

    @Column(name = "image_base64", nullable = false)
    private String ImageBase64;

    @Column(name = "merch_id", nullable = false)
    @JoinColumn(referencedColumnName = "id_merch")
    private Long merchId;
}
