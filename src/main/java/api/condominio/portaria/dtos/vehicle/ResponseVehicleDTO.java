package api.condominio.portaria.dtos.vehicle;

import api.condominio.portaria.dtos.user.ResponseUserDTO;
import api.condominio.portaria.enums.VehicleCategoryEnum;
import api.condominio.portaria.models.embeddable.ApartamentNumber;

import java.time.LocalDateTime;

public record ResponseVehicleDTO(
        String placa,
        ApartamentNumber numApto,
        VehicleCategoryEnum category,
        String color,
        String model,
        String observation,
        LocalDateTime createdAt,
        ResponseUserDTO updated_by
) {}