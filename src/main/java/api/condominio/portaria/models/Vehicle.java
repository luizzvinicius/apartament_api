package api.condominio.portaria.models;

import jakarta.persistence.*;
import api.condominio.portaria.enums.VehicleCategoryEnum;
import api.condominio.portaria.enums.VehicleCategoryConverter;
import api.condominio.portaria.validations.placa.PlacaValidation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;
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
    @PlacaValidation
    @Column(length = 7)
    private String placa;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bloco")
    @JoinColumn(name = "numApto")
    private Apartament apartament;

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

    @Size(max = 100)
    @Column(name = "observacao")
    private String note;

    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", referencedColumnName = "id", nullable = false)
    private User updatedBy;

    public Vehicle(String placa, Apartament apt, VehicleCategoryEnum category, String color, String model, User user) {
        this.placa = placa.toLowerCase(Locale.ROOT);
        this.apartament = apt;
        this.category = category;
        this.color = color;
        this.model = model;
        this.updatedBy = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle vehicle)) return false;
        return Objects.equals(placa, vehicle.placa);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(placa);
    }
}