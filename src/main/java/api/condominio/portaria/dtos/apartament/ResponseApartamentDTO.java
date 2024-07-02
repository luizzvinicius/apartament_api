package api.condominio.portaria.dtos.apartament;

import api.condominio.portaria.models.Resident;
import api.condominio.portaria.models.Vehicle;
import api.condominio.portaria.models.embeddable.ApartamentNumber;

import java.util.List;
import java.util.UUID;

public record ResponseApartamentDTO (
        ApartamentNumber numApto,
        UUID ownerId,
        List<Resident> residents,
        List<Vehicle> vehicles
) { }