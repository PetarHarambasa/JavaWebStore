package hr.algebra.webshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "login_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_login_history")
    private Long idLoginHistory;

    @Column(name = "login_username", nullable = false)
    private String loginUsername;

    @Column(name = "login_email", nullable = false)
    private String loginEmail;

    @Column(name = "date_of_login", nullable = false)
    private Timestamp dateOfLogin;

    @Column(name = "login_address", nullable = false)
    private String loginAddress;
}
