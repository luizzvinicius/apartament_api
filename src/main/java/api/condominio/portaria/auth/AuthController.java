package api.condominio.portaria.auth;

import api.condominio.portaria.auth.dtos.LoginDto;
import api.condominio.portaria.auth.dtos.ResponseLoginDto;
import api.condominio.portaria.auth.dtos.CreateUserRequestDto;
import api.condominio.portaria.auth.dtos.CreateUserResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequestDto user) {
        CreateUserResponseDto infos = authService.createPorteiro(user);
        return ResponseEntity.status(HttpStatus.valueOf(infos.status())).body(infos.message());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDto> loginUser(@RequestBody LoginDto loginDto) {
        var infos = authService.loginUser(loginDto.email(), loginDto.password());
        return ResponseEntity.status(HttpStatus.OK).body(infos);
    }

    @PostMapping("/logout/{id}")
    public ResponseEntity<Void> logoutUser(@PathVariable String id) {
        authService.logoutUser(id);
        return ResponseEntity.ok().build();
    }
}