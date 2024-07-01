package api.condominio.portaria.dtos.resident;

import api.condominio.portaria.models.Resident;
import org.springframework.stereotype.Component;

@Component
public class MapperResident {
    public ResponseResidentDTO toDTO(Resident d) {
        return new ResponseResidentDTO(d.getId(), d.getApartament().getNumApto(), d.getName(), d.getCpf(), d.getPhone());
    }
}