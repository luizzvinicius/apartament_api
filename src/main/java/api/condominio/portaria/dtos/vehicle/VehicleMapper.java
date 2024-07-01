package api.condominio.portaria.dtos.vehicle;

import api.condominio.portaria.models.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {
    public ResponseVehicleDTO toDTO(Vehicle v) {
        return new ResponseVehicleDTO(v.getPlaca(), v.getApartament().getNumApto(), v.getCategory(), v.getColor(), v.getModel(), v.getNote(), v.getCreatedAt());
    }
}