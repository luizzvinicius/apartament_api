package api.condominio.portaria.service;

import api.condominio.portaria.models.User;
import api.condominio.portaria.enums.RoleEnum;
import api.condominio.portaria.dtos.user.MapperUser;
import api.condominio.portaria.dtos.user.CreateUserDTO;
import api.condominio.portaria.dtos.user.ResponseUserDTO;
import api.condominio.portaria.repository.UserRepository;
import api.condominio.portaria.dtos.user.LoginResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Value("${spring.application.name}")
    private String issuer;

    private final UserRepository userRepository;
    private final JwtEncoder encoder;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MapperUser mapper;

    public UserService(UserRepository userRepository, JwtEncoder encoder, BCryptPasswordEncoder passwordEncoder, MapperUser mapper) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public LoginResponseDTO generateToken(Authentication auth) {
        var now = Instant.now();
        var user = this.loadUserByUsername(auth.getName());
        var role = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        var expiresIn = 500L;
        var claims = JwtClaimsSet.builder()
                .issuer(this.issuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .subject(user.getId().toString())
                .claim("scope", role)
                .build();
        var token = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResponseDTO(token, expiresIn);
    }

    public ResponseUserDTO createPorteiro(CreateUserDTO dto) {
        return mapper.toDto(
                userRepository.save(new User(dto.name(), dto.cpf(), dto.email(), passwordEncoder.encode(dto.password()), RoleEnum.PORTEIRO))
        );
    }
}