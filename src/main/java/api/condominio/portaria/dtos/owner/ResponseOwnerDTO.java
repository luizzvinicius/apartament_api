package api.condominio.portaria.dtos.owner;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseOwnerDTO(UUID id,
                               String name,
                               String phone,
                               LocalDateTime createdAt) {
}