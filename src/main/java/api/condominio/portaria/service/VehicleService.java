package api.condominio.portaria.service;

import api.condominio.portaria.dtos.vehicle.*;
import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.enums.VehicleCategoryConverter;
import api.condominio.portaria.models.Vehicle;
import api.condominio.portaria.repository.ApartamentRepository;
import api.condominio.portaria.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Locale;
import java.util.List;

@Service
@Validated
public class VehicleService {
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
        var apartament = apartamentRepository.findByNumAptoBlocoAndNumAptoNumAptoAndStatusEquals(dto.bloco(), dto.numApto(), RecordStatusEnum.ACTIVE)
                .orElseThrow(RuntimeException::new);

        var vehicle = new Vehicle();
        vehicle.setPlaca(dto.placa().toLowerCase(Locale.ROOT));
        vehicle.setApartament(apartament);
        vehicle.setCategory(new VehicleCategoryConverter().convertToEntityAttribute(dto.category()));
        vehicle.setColor(dto.color());
        vehicle.setModel(dto.model());
        if (dto.observation() != null) {
            vehicle.setNote(dto.observation());
        }

        repository.save(vehicle);
        return new ResponseVehicleDTO(
                vehicle.getPlaca(), vehicle.getApartament().getNumApto(),
                vehicle.getCategory(), vehicle.getColor(), vehicle.getModel(),
                vehicle.getNote(), vehicle.getCreatedAt());
    }

    public ResponseVehicleDTO getVehicleByPlaca(String placa) {
        return repository.findById(placa.toLowerCase(Locale.ROOT))
                .map(mapperVehicle::toDTO)
                .orElseThrow(RuntimeException::new);
    }

    public List<ResponseVehicleDTO> getVehicleByBloco(String bloco) {
        return repository.findAllByApartamentNumAptoBlocoEquals(bloco).stream()
                .map(mapperVehicle::toDTO).toList();
    }

    @Transactional
    public ResponseVehicleDTO updateVehicleNote(UpdateNoteDTO dto) {
        var vehicle = repository.findById(dto.placa()).orElseThrow(RuntimeException::new);
        vehicle.setNote(dto.note());
        return mapperVehicle.toDTO(repository.save(vehicle));
    }

    @Transactional
    public void deleteVehicle(String placa) {
        var deleted = repository.deleteByPlaca(placa.toLowerCase(Locale.ROOT));
        if (deleted == 0) {
            throw new RuntimeException("Nenhum veículo deletado");
        }
    }
}