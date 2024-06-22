package api.condominio.portaria.models;

import api.condominio.portaria.enums.VehicleCategoryConverter;
import api.condominio.portaria.enums.VehicleCategoryEnum;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "veiculo", uniqueConstraints = {@UniqueConstraint(name = "Placa veículo duplicada", columnNames = "placa")})
public class Vehicle implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Size(min = 7)
    @Column(length = 7)
    private String placa;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bloco")
    @JoinColumn(name = "numApto")
    private Apartament apartament;

    @NotBlank
    @Column(name = "categoria", nullable = false, length = 7)
    @Convert(converter = VehicleCategoryConverter.class)
    private VehicleCategoryEnum category;

    @NotBlank
    @Size(min = 3, max = 45)
    @Column(name = "cor", nullable = false)
    private String color;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(name = "modelo", nullable = false)
    private String model;

    @NotBlank
    @Size(max = 100)
    @Column(name = "observacao", nullable = false)
    private String note;

    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}