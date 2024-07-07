package api.condominio.portaria.models;

import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.enums.RecordStatusConverter;
import api.condominio.portaria.models.embeddable.ApartamentNumber;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.List;

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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proprietario", referencedColumnName = "id")
    private Owner owner;

    @OneToMany(mappedBy = "apartament", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("name")
    private List<Resident> residents;

    @OneToMany(mappedBy = "apartament", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles;

    @Column(nullable = false, length = 7)
    @Convert(converter = RecordStatusConverter.class)
    private RecordStatusEnum status = RecordStatusEnum.INACTIVE;

    public Apartament(String bloco, String numApto) {
        this.numApto = new ApartamentNumber(bloco, numApto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Apartament that)) return false;
        return Objects.equals(numApto, that.numApto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numApto);
    }
}