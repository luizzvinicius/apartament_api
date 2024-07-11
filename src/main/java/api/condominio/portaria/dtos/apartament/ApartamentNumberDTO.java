package api.condominio.portaria.dtos.apartament;

import api.condominio.portaria.validations.apartament_number.BlocoValidation;
import api.condominio.portaria.validations.apartament_number.NumAptoValidation;

public record ApartamentNumberDTO(
        @BlocoValidation String bloco,
        @NumAptoValidation String numApto
) { }