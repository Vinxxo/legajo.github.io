package proyecto_legajo.legajo.Entity;

import jakarta.persistence.*;
import java.util.Date;

// @Entity - Comentada: no usamos tabla de tokens, usamos JWT en PasswordResetService
// @Table(name = "password_reset_token")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "FK_idUsuario", nullable = false)
    private usuarios usuario;

    @Column(name = "expiracion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiracion;

    public PasswordResetToken() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public usuarios getUsuario() { return usuario; }
    public void setUsuario(usuarios usuario) { this.usuario = usuario; }

    public Date getExpiracion() { return expiracion; }
    public void setExpiracion(Date expiracion) { this.expiracion = expiracion; }

    public boolean isExpired() {
        return expiracion != null && expiracion.before(new Date());
    }
}
