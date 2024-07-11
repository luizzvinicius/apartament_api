package api.condominio.portaria.dtos.vehicle;

import api.condominio.portaria.validations.placa.PlacaValidation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdateNoteDTO(
    @PlacaValidation String placa,
    @NotBlank @Min(4) @Max(100) String note
) { }