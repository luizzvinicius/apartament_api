package api.condominio.portaria.controller;

import api.condominio.portaria.dtos.PhoneDTO;
import api.condominio.portaria.dtos.resident.CreateResidentDTO;
import api.condominio.portaria.dtos.resident.ResponseResidentDTO;
import api.condominio.portaria.service.ResidentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/bloco/{bloco}")
    public ResponseEntity<List<ResponseResidentDTO>> getBlocoResidents(@PathVariable String bloco) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getBlocoResidents(bloco));
    }

    @GetMapping
    public ResponseEntity<List<ResponseResidentDTO>> getResidentsByNumApto(@RequestParam("bloco") String bloco,
                                                                           @RequestParam("numApto") String numApto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getResidentsByNumApto(bloco, numApto));
    }

    @PostMapping("/phone")
    public ResponseEntity<ResponseResidentDTO> updatePhone(@RequestBody @Valid PhoneDTO phone) {
        return ResponseEntity.ok(service.updatePhone(phone));
    }
}