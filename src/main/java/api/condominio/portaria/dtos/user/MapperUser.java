package api.condominio.portaria.dtos.user;

import api.condominio.portaria.models.User;
import org.springframework.stereotype.Component;

@Component
public class MapperUser {
    public ResponseUserDTO toDto(User dto) {
        return new ResponseUserDTO(dto.getId(), dto.getUsername(), dto.getAuthorities(), dto.getCreatedAt());
    }
}