package api.condominio.portaria.dtos.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;
import java.util.Collection;
import java.time.LocalDateTime;

public record ResponseUserDTO(
        UUID id,
        String name,
        Collection<? extends GrantedAuthority> role,
        LocalDateTime created_at
) {}