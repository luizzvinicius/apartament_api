package api.condominio.portaria.auth.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import api.condominio.portaria.auth.dtos.ResponseLoginDto;

import java.util.Map;

@FeignClient(name = "login-client", url = "${app.keycloak.serverUrl}/realms/${app.keycloak.realm}/protocol/openid-connect/token")
public interface LoginClient {
    @PostMapping(consumes = "application/x-www-form-urlencoded")
    ResponseLoginDto loginUser(@RequestBody Map<String, ?> formParams);
}