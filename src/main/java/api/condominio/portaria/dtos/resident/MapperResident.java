package api.condominio.portaria.dtos.resident;

import org.springframework.stereotype.Component;
import api.condominio.portaria.models.Resident;
import api.condominio.portaria.dtos.user.ResponseUserDTO;

@Component
public class MapperResident {
    public ResponseResidentDTO toDTO(Resident d) {
        var user = d.getUpdatedBy();
        return new ResponseResidentDTO(
                d.getId(), d.getApartament().getNumApto(), d.getName(), d.getCpf(), d.getPhone(), d.getCreatedAt(),
                new ResponseUserDTO(user.getId(), user.getName(), user.getAuthorities(), null)
        );
    }
}