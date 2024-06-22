package api.condominio.portaria.models;

import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.enums.RecordStatusConverter;
import api.condominio.portaria.models.embeddable.ApartamentNumber;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "apartamento")
public class Apartament implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @Column(nullable = false)
    private ApartamentNumber numApto;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proprietario", referencedColumnName = "id")
    private Owner owner;

    @OneToMany(mappedBy = "apartament", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Resident> residents;

    @OneToMany(mappedBy = "apartament", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vehicle> vehicles;

    @Column(nullable = false, length = 7)
    @Convert(converter = RecordStatusConverter.class)
    private RecordStatusEnum status;

    @NotNull
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}