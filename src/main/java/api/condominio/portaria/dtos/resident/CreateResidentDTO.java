package api.condominio.portaria.dtos.resident;

import api.condominio.portaria.validations.apartament_number.BlocoValidation;
import api.condominio.portaria.validations.apartament_number.NumAptoValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateResidentDTO(
        @BlocoValidation String bloco,
        @NumAptoValidation String numApto,
        @NotBlank @Size(min = 4, max = 255) String name,
        @NotBlank @Size(min = 11, max = 11) String cpf,
        @NotBlank @Size(max = 11) String phone
) {}