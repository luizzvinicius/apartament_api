package api.condominio.portaria.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PhoneDTO(
        @NotNull UUID id,
        @NotBlank @Size(max = 11) String phone
) {}