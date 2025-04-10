package api.condominio.portaria.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(
    @Email String email, 
    @NotBlank String password) 
{}