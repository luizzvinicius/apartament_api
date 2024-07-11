package api.condominio.portaria.dtos.vehicle;

import api.condominio.portaria.enums.VehicleCategoryEnum;
import api.condominio.portaria.enums.validation.ValueOfEnum;
import api.condominio.portaria.validations.apartament_number.BlocoValidation;
import api.condominio.portaria.validations.apartament_number.NumAptoValidation;
import api.condominio.portaria.validations.placa.PlacaValidation;

public record CreateVehicleDTO(
        @PlacaValidation String placa,
        @BlocoValidation String bloco,
        @NumAptoValidation String numApto,
        @ValueOfEnum(enumClass = VehicleCategoryEnum.class) String category,
        String color,
        String model,
        String observation
) { }