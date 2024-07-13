package api.condominio.portaria.dtos.owner;

import api.condominio.portaria.models.Owner;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MapperOwner {
    public Owner toEntity(CreateOwnerDTO dto) {
        return new Owner(dto.name().toLowerCase(Locale.ROOT), dto.cpf(), dto.phone());
    }

    public ResponseOwnerDTO toDTO(Owner owner) {
        return new ResponseOwnerDTO(owner.getId(), owner.getName(),
                owner.getPhone(), owner.getCreatedAt());
    }
}