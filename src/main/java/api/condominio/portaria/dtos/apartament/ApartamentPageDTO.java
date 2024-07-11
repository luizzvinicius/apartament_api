package api.condominio.portaria.dtos.apartament;

import java.util.List;

public record ApartamentPageDTO(
        List<ResponseApartamentDTO> apartaments,
        int totalPages,
        long size
) { }