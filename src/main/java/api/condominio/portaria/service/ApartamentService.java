package api.condominio.portaria.service;

import api.condominio.portaria.dtos.apartament.ApartamentPageDTO;
import api.condominio.portaria.dtos.apartament.CreateApartamentDTO;
import api.condominio.portaria.dtos.apartament.ResponseApartamentDTO;
import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.models.Apartament;
import api.condominio.portaria.repository.ApartamentRepository;
import api.condominio.portaria.repository.OwnerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ApartamentService {
    private final ApartamentRepository repository;
    private final OwnerRepository ownerRepository;

    public ApartamentService(ApartamentRepository repository, OwnerRepository ownerRepository) {
        this.repository = repository;
        this.ownerRepository = ownerRepository;
    }

    public ResponseApartamentDTO createApartament(CreateApartamentDTO createApartamentDTO) {
        var owner = ownerRepository.findOwnerByIdAndStatusEquals(createApartamentDTO.idProprietario(), RecordStatusEnum.ACTIVE)
                .orElseThrow(RuntimeException::new);

        var apartament = new Apartament(createApartamentDTO.bloco(), createApartamentDTO.numApto(), owner);
        repository.save(apartament);

        return new ResponseApartamentDTO(apartament.getNumApto(), apartament.getOwner().getId(), apartament.getResidents(), apartament.getVehicles(), apartament.getCreatedAt());
    }

    public ResponseApartamentDTO findSpecificApartament(CreateApartamentDTO createApartamentDTO) {
        return repository.findByNumAptoBlocoAndNumAptoNumAptoAndStatusEquals(createApartamentDTO.bloco(), createApartamentDTO.numApto(), RecordStatusEnum.ACTIVE)
                .map(apto ->
                    new ResponseApartamentDTO(
                            apto.getNumApto(),
                            apto.getOwner().getId(),
                            apto.getResidents(),
                            apto.getVehicles(),
                            apto.getCreatedAt()
                    )
                ).orElseThrow(RuntimeException::new);
    }

    public ApartamentPageDTO findApartamentBloco(String bloco, int p, int s) { // validar bloco
        Page<Apartament> page = repository.findAllApartamentBynumAptoBlocoEqualsAndStatusEquals(PageRequest.of(p, s), bloco, RecordStatusEnum.ACTIVE);
        if (page.isEmpty()) {
            throw new RuntimeException();
        }
        return new ApartamentPageDTO(page.toSet(), page.getTotalPages(), page.getTotalElements());
    }
}