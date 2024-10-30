package api.condominio.portaria.dtos.vehicle;

import org.springframework.stereotype.Component;
import api.condominio.portaria.models.Vehicle;

@Component
public class MapperVehicle {
    public ResponseVehicleDTO toDTO(Vehicle v) {
        return new ResponseVehicleDTO(
                v.getPlaca(), v.getApartament().getNumApto(), v.getCategory(), v.getColor(), v.getModel(), v.getNote(), v.getCreatedAt()
        );
    }
}