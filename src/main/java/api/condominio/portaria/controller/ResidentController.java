package api.condominio.portaria.controller;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import api.condominio.portaria.dtos.PhoneDTO;
import api.condominio.portaria.dtos.resident.CreateResidentDTO;
import api.condominio.portaria.dtos.resident.ResponseResidentDTO;
import api.condominio.portaria.dtos.apartament.ApartamentNumberDTO;
import api.condominio.portaria.service.ResidentService;
import api.condominio.portaria.validations.apartament_number.BlocoValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/resident")
public class ResidentController {
    private final ResidentService service;

    public ResidentController(ResidentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseResidentDTO> createResident(Authentication auth, @RequestBody @Valid CreateResidentDTO residentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                service.createResident(UUID.fromString(auth.getName()), residentDTO)
        );
    }

    @GetMapping("/bloco/{bloco}")
    public ResponseEntity<List<ResponseResidentDTO>> getBlocoResidents(@PathVariable @BlocoValidation String bloco) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getBlocoResidents(bloco));
    }

    @GetMapping
    public ResponseEntity<List<ResponseResidentDTO>> getResidentsByNumApto(@RequestBody @Valid ApartamentNumberDTO apartamentNumberDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getResidentsByNumApto(apartamentNumberDTO));
    }

    @PutMapping
    public ResponseEntity<ResponseResidentDTO> updatePhone(@RequestBody @Valid PhoneDTO phone) {
        return ResponseEntity.ok(service.updatePhone(phone));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResident(@PathVariable UUID id) {
        service.deleteResident(id);
    }
}