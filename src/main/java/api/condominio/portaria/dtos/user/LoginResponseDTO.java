package api.condominio.portaria.dtos.user;

public record LoginResponseDTO(
        String token,
        Long expiresIn
) {}