package api.condominio.portaria.models;

import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.enums.RecordStatusConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "morador", uniqueConstraints = {@UniqueConstraint(name = "CPF morador duplicado", columnNames = "cpf")})
public class Resident implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bloco")
    @JoinColumn(name = "numApto")
    private Apartament apartament;

    @NotBlank
    @Column(name = "nome", nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false) // definir length do hash
    private String cpf;

    @Size(min = 11)
    @Column(name = "telefone", nullable = false, length = 11)
    private String phone;

    @NotNull
    @Column(nullable = false, length = 7)
    @Convert(converter = RecordStatusConverter.class)
    private RecordStatusEnum status = RecordStatusEnum.ACTIVE;

    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Resident(Apartament apt, String name, String cpf, String phone) {
        this.apartament = apt;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resident resident)) return false;
        return Objects.equals(id, resident.id) && Objects.equals(cpf, resident.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf);
    }
}