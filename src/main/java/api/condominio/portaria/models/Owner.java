package api.condominio.portaria.models;

import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.enums.RecordStatusConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proprietario", uniqueConstraints = {@UniqueConstraint(name = "CPF Proprietário duplicado", columnNames = "cpf")})
public class Owner implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Apartament> apartaments;

    @NotBlank
    @Column(name = "nome", nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false) // definir length do hash
    private String cpf;

    @Size(min = 11)
    @Column(name = "telefone", nullable = false, length = 11)
    private String phone;

    @Column(nullable = false, length = 7)
    @Convert(converter = RecordStatusConverter.class)
    private RecordStatusEnum status = RecordStatusEnum.ACTIVE;

    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Owner(String name, String cpf, String phone) {
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
    }
}