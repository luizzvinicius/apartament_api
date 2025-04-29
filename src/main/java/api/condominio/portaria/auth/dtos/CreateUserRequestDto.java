package api.condominio.portaria.auth.dtos;

import api.condominio.portaria.enums.RoleEnum;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequestDto(
        @NotBlank @Size(min = 4, max = 255) String name,
        @Email String email,
        @NotBlank String password,
        @NotBlank String cpf,
        @NotBlank RoleEnum role
) {}