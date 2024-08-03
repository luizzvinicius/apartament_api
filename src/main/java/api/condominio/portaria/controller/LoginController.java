package api.condominio.portaria.controller;

import api.condominio.portaria.dtos.user.CreateUserDTO;
import api.condominio.portaria.dtos.user.LoginResponseDTO;
import api.condominio.portaria.dtos.user.ResponseUserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import api.condominio.portaria.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(Authentication auth) { // Já verifica user e password
        var token = userService.generateToken(auth);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/porteiro") // todo criar e lançar exceção de duplicado
    @PreAuthorize("hasAuthority('SCOPE_SINDICO')")
    public ResponseEntity<ResponseUserDTO> createPorteiro(@RequestBody @Valid CreateUserDTO dto) {
        // HttpStatus.UNPROCESSABLE_ENTITY
        return ResponseEntity.ok(userService.createPorteiro(dto));
    }
}