package api.condominio.portaria.dtos.resident;

import api.condominio.portaria.models.embeddable.ApartamentNumber;

import java.util.UUID;

public record ResponseResidentDTO(
        UUID id,
        ApartamentNumber numApto,
        String name,
        String cpf,
        String phone
) { }