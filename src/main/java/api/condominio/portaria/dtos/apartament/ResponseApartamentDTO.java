package api.condominio.portaria.dtos.apartament;

import api.condominio.portaria.models.Resident;
import api.condominio.portaria.models.Vehicle;
import api.condominio.portaria.models.embeddable.ApartamentNumber;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record ResponseApartamentDTO (
        ApartamentNumber numApto,
        UUID ownerId,
        Set<Resident> residents,
        Set<Vehicle> vehicles,
        LocalDateTime createdAt
) { }