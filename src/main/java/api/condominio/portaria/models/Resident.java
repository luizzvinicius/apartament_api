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

    @NotBlank
    @Column(nullable = false, length = 7)
    @Convert(converter = RecordStatusConverter.class)
    private RecordStatusEnum status;

    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}