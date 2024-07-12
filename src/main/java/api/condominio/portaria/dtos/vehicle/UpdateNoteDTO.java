package api.condominio.portaria.dtos.vehicle;

import api.condominio.portaria.validations.placa.PlacaValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateNoteDTO(
    @PlacaValidation String placa,
    @NotBlank @Size(min = 4, max = 100) String note
) { }