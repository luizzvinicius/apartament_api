package api.condominio.portaria.service;

import api.condominio.portaria.dtos.owner.CreateOwnerDTO;
import api.condominio.portaria.dtos.owner.MapperOwner;
import api.condominio.portaria.dtos.owner.ResponseOwnerDTO;
import api.condominio.portaria.exceptions.RecordNotFoundException;
import api.condominio.portaria.models.Owner;
import api.condominio.portaria.repository.ApartamentRepository;
import api.condominio.portaria.repository.OwnerRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;

import java.util.Optional;
import java.util.UUID;

import static api.condominio.portaria.enums.RecordStatusEnum.ACTIVE;
import static api.condominio.portaria.enums.RecordStatusEnum.INACTIVE;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Profile("test")
@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {
    @Mock
    OwnerRepository repository;

    @Mock
    MapperOwner mapperOwnerDTO;

    @Mock
    ApartamentRepository apartamentRepository;

    @InjectMocks
    OwnerService ownerService;

    UUID ownerId;
    Owner owner;
    CreateOwnerDTO createDto;
    ResponseOwnerDTO responseDto;

    @BeforeEach
    void setUp() {
        ownerId = randomUUID();
        owner = new Owner("Luiz", "88877765432", "85497135487");
        owner.setId(ownerId);
        createDto = new CreateOwnerDTO("2", "203", "Luiz", "88877765432", "85497135487");
        responseDto = new ResponseOwnerDTO(ownerId, "Luiz", "88877765432", owner.getCreatedAt());
    }

    @Test
    @DisplayName("Owner não existente no banco")
    void createOwner() {
        when(repository.findByCpfAndStatusEquals("88877765432", INACTIVE)).thenReturn(Optional.empty());

        when(mapperOwnerDTO.toEntity(createDto)).thenReturn(owner);
        when(repository.save(owner)).thenReturn(owner);
        when(apartamentRepository.updateIdProprietario(ownerId, ACTIVE.getValue(), "2", "203", INACTIVE.getValue())).thenReturn(1);
        when(mapperOwnerDTO.toDTO(any(Owner.class))).thenReturn(responseDto);

        var responseOwnerDTO = ownerService.createOwner(createDto);

        SoftAssertions s = new SoftAssertions();
        s.assertThat(owner).isNotNull();
        s.assertThat(responseOwnerDTO).isNotNull().isEqualTo(responseDto);
        verify(repository, times(1)).findByCpfAndStatusEquals(createDto.cpf(), INACTIVE);
        verify(repository, times(1)).save(any(Owner.class));
        verify(apartamentRepository, times(1))
                .updateIdProprietario(owner.getId(), ACTIVE.getValue(), createDto.bloco(), createDto.numApto(), INACTIVE.getValue());
        s.assertAll();
    }

    @Test
    @DisplayName("Owner já existente no banco")
    void createExistingOwner() {
        when(repository.findByCpfAndStatusEquals("88877765432", INACTIVE)).thenReturn(Optional.of(owner));

        when(apartamentRepository.updateIdProprietario(ownerId, ACTIVE.getValue(), "2", "203", INACTIVE.getValue())).thenReturn(1);
        when(mapperOwnerDTO.toDTO(any(Owner.class))).thenReturn(responseDto);

        var responseOwnerDTO = ownerService.createOwner(createDto);

        SoftAssertions s = new SoftAssertions();
        s.assertThat(owner).isNotNull();
        s.assertThat(responseOwnerDTO).isNotNull().isEqualTo(responseDto);
        verify(repository, times(1)).findByCpfAndStatusEquals(createDto.cpf(), INACTIVE);
        verify(repository, times(1)).save(any(Owner.class));
        verify(apartamentRepository, times(1))
                .updateIdProprietario(owner.getId(), ACTIVE.getValue(), createDto.bloco(), createDto.numApto(), INACTIVE.getValue());
        s.assertAll();
    }

    @Test
    @DisplayName("Owner existente, mas número de apartamento inválido")
    void createOwner_apartament_not_exists() {
        when(repository.findByCpfAndStatusEquals("88877765432", INACTIVE)).thenReturn(Optional.empty());

        when(mapperOwnerDTO.toEntity(createDto)).thenReturn(owner);
        when(repository.save(owner)).thenReturn(owner);
        when(apartamentRepository.updateIdProprietario(ownerId, ACTIVE.getValue(), "2", "203", INACTIVE.getValue())).thenReturn(0);

        assertThatThrownBy(() -> ownerService.createOwner(createDto))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessageContaining("NOT FOUND");
    }

    @Test
    void shouldReturnOwnerWhenOwnerExists() {
        owner.setId(ownerId);
        when(repository.findById(ownerId)).thenReturn(Optional.of(owner));
        when(mapperOwnerDTO.toDTO(owner)).thenReturn(responseDto);

        ResponseOwnerDTO result = ownerService.getOwner(ownerId);

        assertThat(result).isEqualTo(responseDto);
    }

    @Test
    void shouldThrowExceptionWhenOwnerNotFound() {
        var id = randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ownerService.getOwner(id))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessageContaining("NOT FOUND");

        verifyNoInteractions(mapperOwnerDTO);
    }
}