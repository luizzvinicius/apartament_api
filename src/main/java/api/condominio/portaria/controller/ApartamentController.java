package api.condominio.portaria.controller;

import api.condominio.portaria.dtos.apartament.ApartamentPageDTO;
import api.condominio.portaria.dtos.apartament.ApartamentNumberDTO;
import api.condominio.portaria.dtos.apartament.ResponseApartamentDTO;
import api.condominio.portaria.service.ApartamentService;
import api.condominio.portaria.validations.apartament_number.BlocoValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/apartament")
public class ApartamentController {
    private final ApartamentService service;

    public ApartamentController(ApartamentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ResponseApartamentDTO> findSpecificApartament(@RequestBody @Valid ApartamentNumberDTO apartamentNumberDTO) {
        return ResponseEntity.ok(service.findSpecificApartament(apartamentNumberDTO));
    }

    @GetMapping("/{bloco}")
    public ResponseEntity<ApartamentPageDTO> findApartamentsBloco(@PathVariable @BlocoValidation String bloco,
                                                                  @RequestParam(defaultValue = "0") @PositiveOrZero int p,
                                                                  @RequestParam(defaultValue = "8") @Positive @Max(16) int s
                                                                  ) {
        return ResponseEntity.ok(service.findApartamentBloco(bloco, p, s));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllRelations(@RequestBody @Valid ApartamentNumberDTO apartamentNumberDTO) {
        service.deleteApartament(apartamentNumberDTO);
        return ResponseEntity.noContent().build();
    }
}