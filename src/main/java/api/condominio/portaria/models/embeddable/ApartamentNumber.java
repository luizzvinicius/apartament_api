package api.condominio.portaria.models.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ApartamentNumber implements Serializable {
    @Column(length = 2)
    private String bloco;

    @Column(length = 3)
    private String numApto;
}