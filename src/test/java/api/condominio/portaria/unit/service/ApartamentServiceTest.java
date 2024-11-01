package api.condominio.portaria.unit.service;

import api.condominio.portaria.dtos.apartament.ApartamentNumberDTO;
import api.condominio.portaria.dtos.apartament.MapperApartament;
import api.condominio.portaria.dtos.apartament.ResponseApartamentDTO;
import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.exceptions.RecordNotFoundException;
import api.condominio.portaria.models.Apartament;
import api.condominio.portaria.models.embeddable.ApartamentNumber;
import api.condominio.portaria.repository.ApartamentRepository;
import api.condominio.portaria.service.ApartamentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ApartamentServiceTest {
    @Mock
    ApartamentRepository apartamentRepository;

    @Mock
    MapperApartament mapperApartament;

    @InjectMocks
    ApartamentService apartamentService;

    Apartament apartament;
    ResponseApartamentDTO apartamentDto;

    @BeforeEach
    void setup() {
        apartament = new Apartament("5", "104");
        apartament.setStatus(RecordStatusEnum.ACTIVE);
        apartamentDto = new ResponseApartamentDTO(
                new ApartamentNumber("5", "104"), UUID.randomUUID(), Collections.emptyList(), Collections.emptyList()
        );
    }

    @Test
    void findSpecificApartament_successfully() {
        when(apartamentRepository.findByNumAptoBlocoAndNumAptoNumAptoAndStatusEquals("5", "104", RecordStatusEnum.ACTIVE))
                .thenReturn(Optional.of(apartament));
        when(mapperApartament.toDTO(apartament)).thenReturn(apartamentDto);

        apartamentService.findSpecificApartament(new ApartamentNumberDTO("5", "104"));

        assertThat(apartament.getNumApto()).isEqualTo(apartamentDto.numApto());
    }

    @Test
    @DisplayName("Apartament not found")
    void findSpecificApartament_apartament_not_found() {
        when(apartamentRepository.findByNumAptoBlocoAndNumAptoNumAptoAndStatusEquals("6", "101", RecordStatusEnum.ACTIVE))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> apartamentService.findSpecificApartament(new ApartamentNumberDTO("6", "101")))
                .isInstanceOf(RecordNotFoundException.class).hasMessageContaining("NOT FOUND");
    }
}