package api.condominio.portaria.dtos.apartament;

import api.condominio.portaria.dtos.resident.ResponseResidentDTO;
import api.condominio.portaria.dtos.vehicle.ResponseVehicleDTO;
import api.condominio.portaria.models.embeddable.ApartamentNumber;

import java.util.List;
import java.util.UUID;

public record ResponseApartamentDTO (
        ApartamentNumber numApto,
        UUID ownerId,
        List<ResponseResidentDTO> residents,
        List<ResponseVehicleDTO> vehicles
) { }