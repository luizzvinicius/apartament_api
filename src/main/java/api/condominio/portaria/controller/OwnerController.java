package api.condominio.portaria.controller;

import api.condominio.portaria.dtos.PhoneDTO;
import api.condominio.portaria.dtos.owner.CreateOwnerDTO;
import api.condominio.portaria.dtos.owner.OwnerPageDTO;
import api.condominio.portaria.dtos.owner.ResponseOwnerDTO;
import api.condominio.portaria.service.OwnerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/owner")
public class OwnerController {
    private final OwnerService service;

    public OwnerController(OwnerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseOwnerDTO> createOwner(@RequestBody @Valid CreateOwnerDTO createOwnerDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createOwner(createOwnerDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseOwnerDTO> getOwner(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOwner(id));
    }

    @GetMapping
    public ResponseEntity<OwnerPageDTO> getOwners(@RequestParam(defaultValue = "0") @PositiveOrZero int p,
                                                  @RequestParam(defaultValue = "16") @Positive @Max(48) int s) {
        return ResponseEntity.ok(service.getOwners(p, s));
    }

    @PutMapping
    public ResponseEntity<ResponseOwnerDTO> updateOwnersPhone(@RequestBody PhoneDTO phone) {
        var owner = service.updateOwnerPhone(phone);
        return ResponseEntity.ok(owner);
    }
}