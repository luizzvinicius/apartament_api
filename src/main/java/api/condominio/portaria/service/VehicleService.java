package api.condominio.portaria.service;

import api.condominio.portaria.dtos.vehicle.*;
import api.condominio.portaria.models.Vehicle;
import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.enums.VehicleCategoryConverter;
import api.condominio.portaria.exceptions.RegisterOverflow;
import api.condominio.portaria.exceptions.RecordNotFoundException;
import api.condominio.portaria.repository.UserRepository;
import api.condominio.portaria.repository.VehicleRepository;
import api.condominio.portaria.repository.ApartamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@Validated
public class VehicleService {
    private static final int MAXVEHICLE = 4;
    private final VehicleRepository repository;
    private final ApartamentRepository apartamentRepository;
    private final MapperVehicle mapperVehicle;
    private final UserRepository userRepository;

    public VehicleService(VehicleRepository repository, ApartamentRepository apartamentRepository, MapperVehicle mapperVehicle, UserRepository userRepository) {
        this.repository = repository;
        this.apartamentRepository = apartamentRepository;
        this.mapperVehicle = mapperVehicle;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseVehicleDTO createVehicle(UUID userId, CreateVehicleDTO dto) {
        var user = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("User"));
        var vehicleQtd = repository.countByApartamentNumAptoBlocoAndApartamentNumAptoNumApto(dto.bloco(), dto.numApto());
        if (vehicleQtd == MAXVEHICLE) {
            throw new RegisterOverflow(MAXVEHICLE, "Vehicles");
        }

        var apartament = apartamentRepository.findByNumAptoBlocoAndNumAptoNumAptoAndStatusEquals(dto.bloco(), dto.numApto(), RecordStatusEnum.ACTIVE)
                .orElseThrow(() -> new RecordNotFoundException("Apartament"));
        var vehicle = new Vehicle(dto.placa(), apartament, new VehicleCategoryConverter().convertToEntityAttribute(dto.category()), dto.color(), dto.model(), user);
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