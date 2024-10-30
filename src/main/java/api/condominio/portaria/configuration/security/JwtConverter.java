package api.condominio.portaria.configuration.security;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Map;
import java.util.List;
import java.util.Collection;
import java.util.stream.Stream;

@Component
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Value("${spring.application.name}")
    private String applicationName;

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
          jwtGrantedAuthoritiesConverter.convert(jwt).stream(), extractClaim(jwt).stream()
        ).toList();
        return new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrincipalClaim(jwt)
        );
    }

    private String getPrincipalClaim(Jwt jwt) {
        var claimName = JwtClaimNames.SUB;
        return jwt.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractClaim(Jwt jwt) {
        if (jwt.getClaim("resource_access") == null) return List.of();

        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");

        if (resourceAccess.get(applicationName) == null ) return List.of();

        Map<String, Object> applicationRoles = (Map<String, Object>) resourceAccess.get(applicationName);
        Collection<String> roles = (Collection<String>) applicationRoles.get("roles");

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }
}