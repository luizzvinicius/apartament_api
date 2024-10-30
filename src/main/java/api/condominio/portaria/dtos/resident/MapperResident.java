package api.condominio.portaria.dtos.resident;

import org.springframework.stereotype.Component;
import api.condominio.portaria.models.Resident;

@Component
public class MapperResident {
    public ResponseResidentDTO toDTO(Resident d) {
        return new ResponseResidentDTO(
                d.getId(), d.getApartament().getNumApto(), d.getName(), d.getCpf(), d.getPhone(), d.getCreatedAt()
        );
    }
}