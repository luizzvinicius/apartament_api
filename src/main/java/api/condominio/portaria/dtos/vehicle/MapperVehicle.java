package api.condominio.portaria.dtos.vehicle;

import org.springframework.stereotype.Component;
import api.condominio.portaria.models.Vehicle;
import api.condominio.portaria.dtos.user.ResponseUserDTO;

@Component
public class MapperVehicle {
    public ResponseVehicleDTO toDTO(Vehicle v) {
        var user = v.getUpdatedBy();
        return new ResponseVehicleDTO(
                v.getPlaca(), v.getApartament().getNumApto(), v.getCategory(), v.getColor(), v.getModel(), v.getNote(), v.getCreatedAt(),
                new ResponseUserDTO(user.getId(), user.getName(), user.getAuthorities(), user.getCreatedAt())
        );
    }
}