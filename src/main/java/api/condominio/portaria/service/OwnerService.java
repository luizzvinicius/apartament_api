package api.condominio.portaria.service;

import api.condominio.portaria.dtos.PhoneDTO;
import api.condominio.portaria.dtos.owner.CreateOwnerDTO;
import api.condominio.portaria.dtos.owner.MapperOwner;
import api.condominio.portaria.dtos.owner.OwnerPageDTO;
import api.condominio.portaria.dtos.owner.ResponseOwnerDTO;
import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.models.Owner;
import api.condominio.portaria.repository.OwnerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OwnerService {
    private final OwnerRepository repository;
    private final MapperOwner mapperOwnerDTO;

    public OwnerService(OwnerRepository repository, MapperOwner mapperOwnerDTO) {
        this.repository = repository;
        this.mapperOwnerDTO = mapperOwnerDTO;
    }

    public ResponseOwnerDTO createOwner(CreateOwnerDTO createOwnerDTO) {
        return mapperOwnerDTO.toDTO(
                repository.save(mapperOwnerDTO.toEntity(createOwnerDTO))
        );
    }

    public ResponseOwnerDTO getOwner(UUID id) throws RuntimeException {
        return repository.findById(id).map(mapperOwnerDTO::toDTO)
                .orElseThrow(RuntimeException::new);
    }

    public OwnerPageDTO getOwners(int p, int size) {
        Page<Owner> page = repository.findAllOwnerByStatusEquals(RecordStatusEnum.ACTIVE, PageRequest.of(p, size));
        if (page.isEmpty()) {
            throw new RuntimeException("pagina vazia");
        }
        return new OwnerPageDTO(page.get().map(mapperOwnerDTO::toDTO).toList(), page.getTotalPages(), page.getTotalElements());
    }

    @Transactional
    public ResponseOwnerDTO updateOwnerPhone(UUID id, PhoneDTO dto) {
        return repository.findById(id).map(owner -> {
            owner.setPhone(dto.phone());
            return mapperOwnerDTO.toDTO(repository.save(owner));
        }).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public void deleteOwner(UUID id) throws RuntimeException {
        var updated = repository.updateOwnerStatus(id, RecordStatusEnum.INACTIVE.getValue());
        if (updated == 0) {
            throw new RuntimeException("Proprietario não encontrado");
        }
    }
}