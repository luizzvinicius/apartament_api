package api.condominio.portaria.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {
    @PreAuthorize("hasRole('ROLE_client_sindico')")
    @GetMapping("/protected")
    public String protectedResource(Authentication auth) {
        System.out.println(auth.toString());
        return "protected resource";
    }

//    @PostMapping("/porteiro") // todo criar e lançar exceção de duplicado
//    @PreAuthorize("hasRole('ROLE_client_sindico')")
//    public ResponseEntity<ResponseUserDTO> createPorteiro(@RequestBody @Valid CreateUserDTO dto) {
//        // HttpStatus.UNPROCESSABLE_ENTITY
//        return ResponseEntity.ok(userService.createPorteiro(dto));
//    }
}