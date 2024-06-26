package api.condominio.portaria.controller;

import api.condominio.portaria.dtos.PhoneDTO;
import api.condominio.portaria.dtos.resident.CreateResidentDTO;
import api.condominio.portaria.dtos.resident.ResponseResidentDTO;
import api.condominio.portaria.service.ResidentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/resident")
public class ResidentController {
    private final ResidentService service;

    public ResidentController(ResidentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseResidentDTO> createResident(@RequestBody @Valid CreateResidentDTO residentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createResident(residentDTO));
    }

    // moradores do bloco ?

    @GetMapping
    public ResponseEntity<Set<ResponseResidentDTO>> getResidentsByNumApto(@RequestParam("bloco") String bloco,
                                                                          @RequestParam("numApto") String numApto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getResidentsByNumApto(bloco, numApto));
    }

    @PostMapping("/phone")
    public ResponseEntity<ResponseResidentDTO> updatePhone(@RequestBody @Valid PhoneDTO phone) {
        return ResponseEntity.ok(service.updatePhone(phone));
    }

    // deleção ver depois
}