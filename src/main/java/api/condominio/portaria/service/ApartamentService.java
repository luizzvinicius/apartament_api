package api.condominio.portaria.service;

import api.condominio.portaria.dtos.apartament.ApartamentPageDTO;
import api.condominio.portaria.dtos.apartament.CreateApartamentDTO;
import api.condominio.portaria.dtos.apartament.MapperApartament;
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
    private final MapperApartament mapperApartament;

    public ApartamentService(ApartamentRepository repository, OwnerRepository ownerRepository, MapperApartament mapperApartament) {
        this.repository = repository;
        this.ownerRepository = ownerRepository;
        this.mapperApartament = mapperApartament;
    }

    public ResponseApartamentDTO createApartament(CreateApartamentDTO createApartamentDTO) {
        var owner = ownerRepository.findOwnerByIdAndStatusEquals(createApartamentDTO.idProprietario(), RecordStatusEnum.ACTIVE)
                .orElseThrow(RuntimeException::new);

        var apartament = new Apartament(createApartamentDTO.bloco(), createApartamentDTO.numApto(), owner);
        var savedApartament = repository.save(apartament);

        return mapperApartament.toDTO(savedApartament);
    }

    public ResponseApartamentDTO findSpecificApartament(CreateApartamentDTO createApartamentDTO) {
        return repository.findByNumAptoBlocoAndNumAptoNumAptoAndStatusEquals(createApartamentDTO.bloco(), createApartamentDTO.numApto(), RecordStatusEnum.ACTIVE)
                .map(mapperApartament::toDTO).orElseThrow(RuntimeException::new);
    }

    public ApartamentPageDTO findApartamentBloco(String bloco, int p, int s) { // validar bloco
        Page<Apartament> page = repository.findAllApartamentBynumAptoBlocoEqualsAndStatusEquals(PageRequest.of(p, s), bloco, RecordStatusEnum.ACTIVE);
        if (page.isEmpty()) {
            throw new RuntimeException();
        }
        return new ApartamentPageDTO(page.toList(), page.getTotalPages(), page.getTotalElements());
    }
}