package hr.algebra.webshop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="shop_user")
@Data
@NoArgsConstructor
public class ShopUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_shop_user")
    private Long idShopUser;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "authenticated")
    private boolean authenticated;

    @Column(name = "user_role_id", nullable = false)
    @JoinColumn(referencedColumnName = "id_user_role")
    private Long userRoleId;

    public ShopUser(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
