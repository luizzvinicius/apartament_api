package api.condominio.portaria.dtos.apartament;

import api.condominio.portaria.models.Apartament;

import java.util.List;

public record ApartamentPageDTO(
        List<Apartament> apartaments,
        int totalPages,
        long size
) { }