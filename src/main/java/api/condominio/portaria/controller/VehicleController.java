package api.condominio.portaria.controller;

import api.condominio.portaria.dtos.vehicle.CreateVehicleDTO;
import api.condominio.portaria.dtos.vehicle.ResponseVehicleDTO;
import api.condominio.portaria.dtos.vehicle.UpdateNoteDTO;
import api.condominio.portaria.service.VehicleService;
import api.condominio.portaria.validations.apartament_number.BlocoValidation;
import api.condominio.portaria.validations.placa.PlacaValidation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController {
    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseVehicleDTO> createVehicle(@RequestBody @Valid CreateVehicleDTO createValues) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createVehicle(createValues));
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<ResponseVehicleDTO> getVehicleByplaca(@PathVariable @PlacaValidation String placa) {
        return ResponseEntity.ok(service.getVehicleByPlaca(placa));
    }

    @GetMapping("/bloco/{bloco}")
    public ResponseEntity<List<ResponseVehicleDTO>> getVehiclesByBloco(@PathVariable @BlocoValidation String bloco) {
        return ResponseEntity.ok(service.getVehicleByBloco(bloco));
    }

    @PutMapping("/note")
    public ResponseEntity<ResponseVehicleDTO> updateVehicleNote(@RequestBody @Valid UpdateNoteDTO updateNoteDTO) {
        return ResponseEntity.ok(service.updateVehicleNote(updateNoteDTO));
    }

    @DeleteMapping("/{placa}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable @PlacaValidation String placa) {
        service.deleteVehicle(placa);
    }
}