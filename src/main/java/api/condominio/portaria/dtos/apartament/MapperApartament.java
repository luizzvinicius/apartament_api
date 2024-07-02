package api.condominio.portaria.dtos.apartament;

import api.condominio.portaria.models.Apartament;
import org.springframework.stereotype.Component;

@Component
public class MapperApartament {
    public ResponseApartamentDTO toDTO(Apartament p) {
        return new ResponseApartamentDTO(p.getNumApto(), p.getOwner().getId(), p.getResidents(), p.getVehicles());
    }
}