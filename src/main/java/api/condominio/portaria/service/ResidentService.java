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

import java.util.Set;
import java.util.stream.Collectors;

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

    public ResponseResidentDTO createResident(CreateResidentDTO residentDTO) {
        var apartament = apartamentRepository.findById(new ApartamentNumber(residentDTO.bloco(), residentDTO.numApto()))
                .orElseThrow(RuntimeException::new);
        var resident = new Resident(residentDTO.name(), residentDTO.cpf(), residentDTO.phone());
        resident.setApartament(apartament);

        var savedResident = repository.save(resident);
        return mapperResident.toDTO(savedResident);
    }

    public Set<ResponseResidentDTO> getBlocoResidents(String bloco) {
        return repository.findByApartamentNumAptoBlocoAndStatusEquals(bloco, RecordStatusEnum.ACTIVE)
                .stream().map(mapperResident::toDTO).collect(Collectors.toSet());
    }

    public Set<ResponseResidentDTO> getResidentsByNumApto(String bloco, String numApto) {
        return repository.findByApartamentNumAptoBlocoAndApartamentNumAptoNumAptoAndStatusEquals(bloco, numApto, RecordStatusEnum.ACTIVE)
                .stream().map(mapperResident::toDTO).collect(Collectors.toSet());
    }

    public ResponseResidentDTO updatePhone(PhoneDTO phone) {
        var resident = repository.findById(phone.id()).orElseThrow(RuntimeException::new);
        resident.setPhone(phone.phone());
        var updatedResident = repository.save(resident);
        return mapperResident.toDTO(updatedResident);
    }
}