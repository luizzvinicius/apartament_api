package api.condominio.portaria.dtos.vehicle;

import api.condominio.portaria.enums.VehicleCategoryEnum;
import api.condominio.portaria.enums.validation.ValueOfEnum;
import api.condominio.portaria.validations.apartament_number.BlocoValidation;
import api.condominio.portaria.validations.apartament_number.NumAptoValidation;
import api.condominio.portaria.validations.placa.PlacaValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateVehicleDTO(
        @PlacaValidation String placa,
        @BlocoValidation String bloco,
        @NumAptoValidation String numApto,
        @ValueOfEnum(enumClass = VehicleCategoryEnum.class) String category,
        @NotBlank @Size(min = 3, max = 45) String color,
        @NotBlank @Size(min = 2, max = 50) String model,
        @Size(max = 100) String observation
) { }