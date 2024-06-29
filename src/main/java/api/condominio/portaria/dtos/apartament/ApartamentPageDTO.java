package api.condominio.portaria.dtos.apartament;

import api.condominio.portaria.models.Apartament;

import java.util.Set;

public record ApartamentPageDTO(
        Set<Apartament> apartaments,
        int totalPages,
        long size
) { }