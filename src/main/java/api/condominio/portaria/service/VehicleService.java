package api.condominio.portaria.service;

import api.condominio.portaria.dtos.vehicle.CreateVehicleDTO;
import api.condominio.portaria.dtos.vehicle.MapperVehicle;
import api.condominio.portaria.dtos.vehicle.ResponseVehicleDTO;
import api.condominio.portaria.dtos.vehicle.UpdateNoteDTO;
import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.enums.VehicleCategoryConverter;
import api.condominio.portaria.exceptions.RecordNotFoundException;
import api.condominio.portaria.exceptions.RegisterOverflowException;
import api.condominio.portaria.models.Vehicle;
import api.condominio.portaria.repository.ApartamentRepository;
import api.condominio.portaria.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Locale;

@Service
@Validated
public class VehicleService {
    private static final int MAXVEHICLE = 4;
    private final VehicleRepository repository;
    private final ApartamentRepository apartamentRepository;
    private final MapperVehicle mapperVehicle;

    public VehicleService(VehicleRepository repository, ApartamentRepository apartamentRepository, MapperVehicle mapperVehicle) {
        this.repository = repository;
        this.apartamentRepository = apartamentRepository;
        this.mapperVehicle = mapperVehicle;
    }

    @Transactional
    public ResponseVehicleDTO createVehicle(CreateVehicleDTO dto) {
        var vehicleQtd = repository.countByApartamentNumAptoBlocoAndApartamentNumAptoNumApto(dto.bloco(), dto.numApto());
        if (vehicleQtd == MAXVEHICLE) {
            throw new RegisterOverflowException(MAXVEHICLE, "Vehicles");
        }

        var apartament = apartamentRepository.findByNumAptoBlocoAndNumAptoNumAptoAndStatusEquals(dto.bloco(), dto.numApto(), RecordStatusEnum.ACTIVE)
                .orElseThrow(() -> new RecordNotFoundException("Apartament"));
        var vehicle = new Vehicle(dto.placa(), apartament, new VehicleCategoryConverter().convertToEntityAttribute(dto.category()), dto.color(), dto.model());
        if (dto.observation() != null) {
            vehicle.setNote(dto.observation());
        }

        return mapperVehicle.toDTO(
                repository.save(vehicle)
        );
    }

    public ResponseVehicleDTO getVehicleByPlaca(String placa) {
        return repository.findById(placa.toLowerCase(Locale.ROOT))
                .map(mapperVehicle::toDTO)
                .orElseThrow(() -> new RecordNotFoundException("Vehicle"));
    }

    public List<ResponseVehicleDTO> getVehicleByBloco(String bloco) {
        List<Vehicle> vehicles = repository.findAllByApartamentNumAptoBlocoEquals(bloco);
        if (vehicles.isEmpty()) {
            throw new RecordNotFoundException("Vehicle");
        }
        return vehicles.stream().map(mapperVehicle::toDTO).toList();
    }

    @Transactional
    public ResponseVehicleDTO updateVehicleNote(UpdateNoteDTO dto) {
        var vehicle = repository.findById(dto.placa()).orElseThrow(() -> new RecordNotFoundException("Vehicle"));
        vehicle.setNote(dto.note());
        return mapperVehicle.toDTO(repository.save(vehicle));
    }

    @Transactional
    public void deleteVehicle(String placa) {
        var deleted = repository.deleteByPlaca(placa.toLowerCase(Locale.ROOT));
        if (deleted == 0) {
            throw new RecordNotFoundException("Vehicle");
        }
    }
}