package api.condominio.portaria.dtos.vehicle;

import api.condominio.portaria.enums.VehicleCategoryEnum;
import api.condominio.portaria.enums.validation.ValueOfEnum;

public record CreateVehicleDTO(
        String placa,
        String bloco,
        String numApto,
        @ValueOfEnum(enumClass = VehicleCategoryEnum.class) String category,
        String color,
        String model,
        String observation
) { }