package api.condominio.portaria.models.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartamentNumber implements Serializable {
    @Column(length = 2)
    private String bloco;

    @Column(length = 3)
    private String numApto;
}