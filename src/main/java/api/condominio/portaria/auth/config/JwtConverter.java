package api.condominio.portaria.auth.config;

import api.condominio.portaria.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    @Value("${app.keycloak.realm}")
    private String applicationName;

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(), extractRoles(jwt).stream()
        ).toList();
        return new JwtAuthenticationToken(
                jwt,
                authorities,
                jwt.getClaim("sub")
        );
    }

    public Map<String, Object> getCustomClaims(Jwt jwt) {
        UUID id = UUID.fromString(jwt.getClaim("sub"));
        RoleEnum role = RoleEnum.valueOf(extractRoles(jwt).getFirst().toString().split("_")[1]);
        boolean isEmailVerified = jwt.getClaim("email_verified");
        String email = jwt.getClaim("email");
        return Map.of("id", id, "role", role, "email", email, "isEmailVerified", isEmailVerified);
    }

    private List<? extends GrantedAuthority> extractRoles(Jwt jwt) {
        if (jwt.getClaim("resource_access") == null) return List.of();

        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");

        if (resourceAccess.get(applicationName) == null) return List.of();

        Map<String, Object> applicationRoles = (Map<String, Object>) resourceAccess.get(applicationName);
        Collection<String> roles = (Collection<String>) applicationRoles.get("roles");

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }
}