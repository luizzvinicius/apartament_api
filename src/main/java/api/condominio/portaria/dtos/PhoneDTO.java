package api.condominio.portaria.dtos;

import java.util.UUID;

public record PhoneDTO(
        UUID id,
        String phone
) { }