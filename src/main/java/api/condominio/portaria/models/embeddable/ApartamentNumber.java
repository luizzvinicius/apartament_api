package api.condominio.portaria.models.embeddable;

import api.condominio.portaria.validations.apartament_number.BlocoValidation;
import api.condominio.portaria.validations.apartament_number.NumAptoValidation;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ApartamentNumber implements Serializable {
    @BlocoValidation
    @Column(length = 2)
    private String bloco;

    @NumAptoValidation
    @Column(length = 3)
    private String numApto;

    public ApartamentNumber() {
    }

    public ApartamentNumber(String bloco, String numApto) {
        this.bloco = bloco;
        this.numApto = numApto;
    }

    public String getBloco() {
        return this.bloco;
    }

    public String getNumApto() {
        return this.numApto;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }

    public void setNumApto(String numApto) {
        this.numApto = numApto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApartamentNumber that)) return false;
        return Objects.equals(bloco, that.bloco) && Objects.equals(numApto, that.numApto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bloco, numApto);
    }
}