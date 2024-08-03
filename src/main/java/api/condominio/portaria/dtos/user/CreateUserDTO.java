package api.condominio.portaria.dtos.user;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO (
        @NotBlank String cpf,
        @NotBlank @Size(min = 4, max = 255) String name,
        @Email String email,
        @NotBlank String password
) {}