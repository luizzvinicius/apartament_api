package api.condominio.portaria.dtos.resident;

import api.condominio.portaria.models.embeddable.ApartamentNumber;
import api.condominio.portaria.dtos.user.ResponseUserDTO;

import java.util.UUID;
import java.time.LocalDateTime;

public record ResponseResidentDTO(
        UUID id,
        ApartamentNumber numApto,
        String name,
        String cpf,
        String phone,
        LocalDateTime createdAt,
        ResponseUserDTO updated_by
) {}