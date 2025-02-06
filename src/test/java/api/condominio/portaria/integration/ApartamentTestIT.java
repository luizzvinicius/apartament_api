package api.condominio.portaria.integration;

import api.condominio.portaria.dtos.apartament.ApartamentNumberDTO;
import api.condominio.portaria.dtos.owner.CreateOwnerDTO;
import api.condominio.portaria.dtos.resident.CreateResidentDTO;
import api.condominio.portaria.dtos.vehicle.CreateVehicleDTO;
import api.condominio.portaria.enums.RecordStatusEnum;
import api.condominio.portaria.models.Apartament;
import api.condominio.portaria.repository.ApartamentRepository;
import api.condominio.portaria.repository.OwnerRepository;
import api.condominio.portaria.repository.ResidentRepository;
import api.condominio.portaria.repository.VehicleRepository;
import api.condominio.portaria.service.OwnerService;
import api.condominio.portaria.service.ResidentService;
import api.condominio.portaria.service.VehicleService;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ApartamentTestIT {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    ApartamentRepository apartamentRepository;

    @Autowired
    OwnerService ownerService;

    @Autowired
    ResidentService residentService;

    @Autowired
    ResidentRepository residentRepository;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    VehicleRepository vehicleRepository;

//    @TestConfiguration
//    @Lazy
//    static class Config {
//        @Bean(name = "testRestTemplate")
//        public TestRestTemplate restTemplate(@Value("${local.server.port}") int port) {
//            var template = new RestTemplateBuilder()
//                    .rootUri("http://localhost:8080/realms/API_apartament/protocol/openid-connect/token")
////                    .rootUri("http://localhost:" + port)
//                    .basicAuthentication("luiz_sindico", "admin");
//            return new TestRestTemplate(template);
//        }
//    }

    @BeforeEach
    void init() {
        apartamentRepository.save(new Apartament("2", "203"));
        ownerService.createOwner(new CreateOwnerDTO("2", "203", "Luiz", "12762393000", "82999999999"));
        residentService.createResident(new CreateResidentDTO("2", "203", "Jorge", "18789643054", "82999999999"));
        vehicleService.createVehicle(new CreateVehicleDTO("muv1122", "2", "203", "carro", "branca", "nada", "nada"));
    }

    @Test
    @DisplayName("Must delete the apartament and its relations")
    void deleteAllRelations() {
        var url = testRestTemplate.getRootUri() + "/api/v1/apartament/bloco/2/apt/203";

        testRestTemplate.delete(url);

        var owner = ownerRepository.findByCpfAndStatusEquals("12762393000", RecordStatusEnum.ACTIVE);
        var residents = residentRepository.findByApartamentNumAptoBlocoAndApartamentNumAptoNumAptoAndStatusEquals("2", "203", RecordStatusEnum.ACTIVE);
        var vehicle = vehicleRepository.countByApartamentNumAptoBlocoAndApartamentNumAptoNumApto("2", "203");

        SoftAssertions s = new SoftAssertions();
        s.assertThat(owner).isEmpty();
        s.assertThat(residents).size().isEqualTo(0);
        s.assertThat(vehicle).isEqualTo(0);
        s.assertAll();
    }
}