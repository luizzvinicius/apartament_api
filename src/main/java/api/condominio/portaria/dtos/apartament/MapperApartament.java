package api.condominio.portaria.dtos.apartament;

import api.condominio.portaria.dtos.resident.MapperResident;
import api.condominio.portaria.dtos.vehicle.MapperVehicle;
import api.condominio.portaria.models.Apartament;
import org.springframework.stereotype.Component;

@Component
public class MapperApartament {
    private final MapperResident mapperResident;
    private final MapperVehicle mapperVehicle;

    public MapperApartament(MapperResident mapperResident, MapperVehicle mapperVehicle) {
        this.mapperResident = mapperResident;
        this.mapperVehicle = mapperVehicle;
    }

    public ResponseApartamentDTO toDTO(Apartament p) {
        var residents = p.getResidents().stream().map(mapperResident::toDTO).toList();
        var vehicles = p.getVehicles().stream().map(mapperVehicle::toDTO).toList();
        return new ResponseApartamentDTO(p.getNumApto(), p.getOwner().getId(), residents, vehicles);
    }
}