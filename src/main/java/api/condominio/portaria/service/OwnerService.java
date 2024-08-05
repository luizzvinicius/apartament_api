package api.condominio.portaria.service;

import api.condominio.portaria.dtos.owner.*;
import api.condominio.portaria.dtos.PhoneDTO;
import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.exceptions.RecordNotFoundException;
import api.condominio.portaria.models.Owner;
import api.condominio.portaria.repository.ApartamentRepository;
import api.condominio.portaria.repository.OwnerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class OwnerService {
    private final OwnerRepository repository;
    private final MapperOwner mapperOwnerDTO;
    private final ApartamentRepository apartamentRepository;

    public OwnerService(OwnerRepository repository, MapperOwner mapperOwnerDTO, ApartamentRepository apRepository) {
        this.repository = repository;
        this.mapperOwnerDTO = mapperOwnerDTO;
        this.apartamentRepository = apRepository;
    }

    @Transactional
    public ResponseOwnerDTO createOwner(CreateOwnerDTO createOwnerDTO) {
        Owner owner;
        Optional<Owner> optOwner = repository.findByCpfAndStatusEquals(createOwnerDTO.cpf(), RecordStatusEnum.INACTIVE);

        if (optOwner.isEmpty()) {
            owner = repository.save(mapperOwnerDTO.toEntity(createOwnerDTO));
        } else {
            owner = optOwner.get();
            repository.updateOwnerStatus(optOwner.get().getId(), RecordStatusEnum.ACTIVE.getValue(), RecordStatusEnum.INACTIVE.getValue());
        }

        var update = apartamentRepository.updateIdProprietario(
                owner.getId(), RecordStatusEnum.ACTIVE.getValue(), createOwnerDTO.bloco(), createOwnerDTO.numApto(), RecordStatusEnum.INACTIVE.getValue()
        );

        if (update == 0) {
            throw new RecordNotFoundException("Apartament");
        }

        return mapperOwnerDTO.toDTO(owner);
    }

    public ResponseOwnerDTO getOwner(UUID id) {
        return repository.findById(id).map(mapperOwnerDTO::toDTO)
                .orElseThrow(() -> new RecordNotFoundException("Owner"));
    }

    public OwnerPageDTO getOwners(int p, int size) {
        Page<Owner> page = repository.findAllByStatusEquals(RecordStatusEnum.ACTIVE, PageRequest.of(p, size));
        if (page.isEmpty()) {
            throw new RecordNotFoundException("Owner page");
        }
        return new OwnerPageDTO(page.get().map(mapperOwnerDTO::toDTO).toList(), page.getTotalPages(), page.getTotalElements());
    }

    @Transactional
    public ResponseOwnerDTO updateOwnerPhone(PhoneDTO dto) {
        return repository.findById(dto.id()).map(owner -> {
            owner.setPhone(dto.phone());
            return mapperOwnerDTO.toDTO(repository.save(owner));
        }).orElseThrow(() -> new RecordNotFoundException("Owner"));
    }
}