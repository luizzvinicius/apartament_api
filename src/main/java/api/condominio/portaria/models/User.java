package api.condominio.portaria.models;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import api.condominio.portaria.enums.RoleEnum;
import api.condominio.portaria.enums.RoleEnumConverter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.Collection;
import java.time.LocalDateTime;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios", uniqueConstraints = {
        @UniqueConstraint(name = "usuário existente", columnNames = "email"),
        @UniqueConstraint(name = "cpf já cadastrado", columnNames = "cpf")
})
public class User implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Getter
    @NotBlank
    @Column(name = "nome", nullable = false)
    private String name;

    @Getter
    @NotBlank
    @Column(nullable = false)
    private String cpf;

    @Getter
    @Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(name = "senha", nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false, length = 10)
    @Convert(converter = RoleEnumConverter.class)
    private RoleEnum role;

    @Getter
    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public User(String name, String cpf, String email, String password, RoleEnum role) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}