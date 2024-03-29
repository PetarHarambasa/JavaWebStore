package hr.algebra.webshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="category")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_category")
    private Long idCategory;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "front_image_base64", nullable = false)
    private String frontImageBase64;
}
