package api.condominio.portaria.controller;

import api.condominio.portaria.dtos.apartament.ApartamentPageDTO;
import api.condominio.portaria.dtos.apartament.CreateApartamentDTO;
import api.condominio.portaria.dtos.apartament.ResponseApartamentDTO;
import api.condominio.portaria.service.ApartamentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/apartament")
public class ApartamentController {
    private final ApartamentService service;

    public ApartamentController(ApartamentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseApartamentDTO> createApartament(@RequestBody @Valid CreateApartamentDTO createApartamentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createApartament(createApartamentDTO));
    }

    @GetMapping
    public ResponseEntity<ResponseApartamentDTO> findSpecificApartament(@RequestBody @Valid CreateApartamentDTO createApartamentDTO) {
        return ResponseEntity.ok(service.findSpecificApartament(createApartamentDTO));
    }

    @GetMapping("/{bloco}")
    public ResponseEntity<ApartamentPageDTO> findApartamentsBloco(@PathVariable String bloco,
                                                                  @RequestParam(defaultValue = "0") @PositiveOrZero int p,
                                                                  @RequestParam(defaultValue = "8") @Positive @Max(16) int s
                                                                  ) {
        return ResponseEntity.ok(service.findApartamentBloco(bloco, p, s));
    }

    @DeleteMapping("/{bloco}/{numApto}")
    public ResponseEntity<Void> deleteAllRelations(@PathVariable String bloco,
                                                   @PathVariable String numApto) {
        service.deleteApartament(bloco, numApto);
        return ResponseEntity.noContent().build();
    }
}