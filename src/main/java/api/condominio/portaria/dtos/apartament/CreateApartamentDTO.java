package api.condominio.portaria.dtos.apartament;

import java.util.UUID;

public record CreateApartamentDTO(
        String bloco,
        String numApto,
        UUID idProprietario
) { }