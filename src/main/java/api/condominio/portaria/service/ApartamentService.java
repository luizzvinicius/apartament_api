package api.condominio.portaria.service;

import api.condominio.portaria.exceptions.RecordNotFoundException;
import api.condominio.portaria.repository.*;
import api.condominio.portaria.dtos.apartament.*;
import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.models.Apartament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApartamentService {
    private final ApartamentRepository repository;
    private final OwnerRepository ownerRepository;
    private final ResidentRepository residentRepository;
    private final VehicleRepository vehicleRepository;
    private final MapperApartament mapperApartament;

    public ApartamentService(ApartamentRepository repository, OwnerRepository ownerRepository, MapperApartament mapperApartament, ResidentRepository residentRepository, VehicleRepository vehicleRepository) {
        this.repository = repository;
        this.ownerRepository = ownerRepository;
        this.mapperApartament = mapperApartament;
        this.residentRepository = residentRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public ResponseApartamentDTO findSpecificApartament(ApartamentNumberDTO apartamentNumberDTO) {
        return repository.findByNumAptoBlocoAndNumAptoNumAptoAndStatusEquals(apartamentNumberDTO.bloco(), apartamentNumberDTO.numApto(), RecordStatusEnum.ACTIVE)
                .map(mapperApartament::toDTO).orElseThrow(() -> new RecordNotFoundException("Apartament"));
    }

    public ApartamentPageDTO findApartamentBloco(String bloco, int p, int s) {
        Page<Apartament> page = repository.findAllApartamentBynumAptoBlocoEqualsAndStatusEquals(PageRequest.of(p, s), bloco, RecordStatusEnum.ACTIVE);
        if (page.isEmpty()) {
            throw new RecordNotFoundException("Apartament");
        }
        var apartamets = page.get().map(mapperApartament::toDTO).toList();
        return new ApartamentPageDTO(apartamets, page.getTotalPages(), page.getTotalElements());
    }

    @Transactional
    public void deleteApartament(ApartamentNumberDTO aptNumberDTO) {
        var bloco = aptNumberDTO.bloco();
        var numApto = aptNumberDTO.numApto();
        var apartament = repository.findByNumAptoBlocoAndNumAptoNumAptoAndStatusEquals(bloco, numApto, RecordStatusEnum.ACTIVE)
                .orElseThrow(() -> new RecordNotFoundException("Apartament"));
        var ownerId = apartament.getOwner().getId();

        var registeredApartaments = repository.countByOwnerIdAndStatusEquals(ownerId, RecordStatusEnum.ACTIVE);
        if (registeredApartaments == 1) {
            ownerRepository.updateOwnerStatus(ownerId, RecordStatusEnum.INACTIVE.getValue(), RecordStatusEnum.ACTIVE.getValue());
        }

        var vehicles = apartament.getVehicles();
        while (!vehicles.isEmpty()) {
            vehicleRepository.delete(vehicles.removeFirst());
        }

        residentRepository.updateAllResidentsStatus(bloco, numApto, RecordStatusEnum.INACTIVE.getValue());
        repository.updateStatus(bloco, numApto, RecordStatusEnum.INACTIVE.getValue());
    }
}