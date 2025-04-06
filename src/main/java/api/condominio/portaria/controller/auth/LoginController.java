package api.condominio.portaria.controller.auth;

import api.condominio.portaria.configuration.security.JwtConverter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {
    private final JwtConverter jwtConverter;

    public LoginController(JwtConverter jwtConverter) {
        this.jwtConverter = jwtConverter;
    }

    @PreAuthorize("hasRole('ROLE_PORTEIRO')")
    @GetMapping("/protected")
    public String protectedResource(@AuthenticationPrincipal Jwt token) {
        System.out.println(token);
        System.out.println(jwtConverter.getCustomClaims(token));
        return "protected resource";
    }

//    @PostMapping("/porteiro") // todo criar e lançar exceção de duplicado
//    @PreAuthorize("hasRole('ROLE_client_sindico')")
//    public ResponseEntity<ResponseUserDTO> createPorteiro(@RequestBody @Valid CreateUserDTO dto) {
//        // HttpStatus.UNPROCESSABLE_ENTITY
//        return ResponseEntity.ok(userService.createPorteiro(dto));
//    }
}