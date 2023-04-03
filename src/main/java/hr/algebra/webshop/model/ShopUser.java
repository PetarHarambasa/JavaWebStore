package hr.algebra.webshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ShopUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idShopUser;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private boolean authenticated;
    @Column(nullable = false)
    @JoinColumn(referencedColumnName = "id_user_role")
    private Long userRoleId;
}
