package api.condominio.portaria.dtos.owner;

import java.util.List;

public record OwnerPageDTO(
        List<ResponseOwnerDTO> owners,
        int totalPages,
        long totalElements
) {}