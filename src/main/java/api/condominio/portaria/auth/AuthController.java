package api.condominio.portaria.auth;

import api.condominio.portaria.dtos.user.CreateUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody CreateUserDto user) {
        var infos = authService.createPorteiro(user);
        return ResponseEntity.status(Integer.parseInt(infos.get(0))).body(infos.get(1));
    }

    @PostMapping("/logout/{id}")
    public ResponseEntity<Void> logoutUser(@PathVariable String id) {
        authService.logoutUser(id);
        return ResponseEntity.ok().build();
    }
}