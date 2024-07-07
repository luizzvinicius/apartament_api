package api.condominio.portaria.service;

import api.condominio.portaria.dtos.PhoneDTO;
import api.condominio.portaria.dtos.resident.CreateResidentDTO;
import api.condominio.portaria.dtos.resident.MapperResident;
import api.condominio.portaria.dtos.resident.ResponseResidentDTO;
import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.models.Resident;
import api.condominio.portaria.models.embeddable.ApartamentNumber;
import api.condominio.portaria.repository.ApartamentRepository;
import api.condominio.portaria.repository.ResidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResidentService {
    private final ResidentRepository repository;
    private final ApartamentRepository apartamentRepository;
    private final MapperResident mapperResident;

    public ResidentService(ResidentRepository repository, ApartamentRepository apRepository, MapperResident mapperResident) {
        this.repository = repository;
        this.apartamentRepository = apRepository;
        this.mapperResident = mapperResident;
    }

    @Transactional
    public ResponseResidentDTO createResident(CreateResidentDTO residentDTO) {
        var apartament = apartamentRepository.findById(new ApartamentNumber(residentDTO.bloco(), residentDTO.numApto()))
                .orElseThrow(RuntimeException::new);

        Resident resident;
        Optional<Resident> optResident = repository.findByCpfEquals(residentDTO.cpf());
        if (optResident.isEmpty()) {
            resident = repository.save(new Resident(apartament, residentDTO.name().toLowerCase(Locale.ROOT), residentDTO.cpf(), residentDTO.phone()));
        } else {
            resident = optResident.get();
            repository.updateResidentStatus(resident.getId(), RecordStatusEnum.ACTIVE.getValue());
        }

        return mapperResident.toDTO(resident);
    }

    public List<ResponseResidentDTO> getBlocoResidents(String bloco) {
        return repository.findByApartamentNumAptoBlocoAndStatusEquals(bloco, RecordStatusEnum.ACTIVE)
                .stream().map(mapperResident::toDTO).toList();
    }

    public List<ResponseResidentDTO> getResidentsByNumApto(String bloco, String numApto) {
        return repository.findByApartamentNumAptoBlocoAndApartamentNumAptoNumAptoAndStatusEquals(bloco, numApto, RecordStatusEnum.ACTIVE)
                .stream().map(mapperResident::toDTO).toList();
    }

    @Transactional
    public ResponseResidentDTO updatePhone(PhoneDTO phone) {
        var resident = repository.findById(phone.id()).orElseThrow(RuntimeException::new);
        resident.setPhone(phone.phone());
        var updatedResident = repository.save(resident);
        return mapperResident.toDTO(updatedResident);
    }

    @Transactional
    public void deleteResident(UUID id) {
        var update = repository.updateResidentStatus(id, RecordStatusEnum.INACTIVE.getValue());
        if (update == 0) {
            throw new RuntimeException("Morador não encontrado");
        }
    }
}