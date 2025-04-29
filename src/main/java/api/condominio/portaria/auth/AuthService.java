package api.condominio.portaria.auth;

import api.condominio.portaria.auth.dtos.ResponseLoginDto;
import api.condominio.portaria.auth.feignclients.LoginClient;
import api.condominio.portaria.auth.dtos.CreateUserRequestDto;
import api.condominio.portaria.auth.dtos.CreateUserResponseDto;
import api.condominio.portaria.enums.RoleEnum;
import api.condominio.portaria.exceptions.UserNotCreatedException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {
    @Value("${app.keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;
    private final LoginClient loginClient;

    public AuthService(Keycloak keycloak, LoginClient loginClient) {
        this.keycloak = keycloak;
        this.loginClient = loginClient;
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    private UserRepresentation getUserRepresentation(CreateUserRequestDto user, RoleEnum role) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(user.name());
        userRepresentation.setEmail(user.email());
        userRepresentation.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(user.password());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        userRepresentation.setGroups(List.of(role.toString() + "S"));
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        return userRepresentation;
    }

    public CreateUserResponseDto createPorteiro(CreateUserRequestDto user) {
        UserRepresentation userRepresentation = getUserRepresentation(user, RoleEnum.PORTEIRO);

        String returnStatus = "201";
        String message = "Usuário criado";
        try (Response response = getUsersResource().create(userRepresentation)) {
            List<UserRepresentation> userFromKeycloak = getUsersResource().searchByEmail(user.email(), true);
            System.out.println(response.getStatus());
            if (response.getStatus() != 201 || userFromKeycloak.isEmpty()) {
                throw new UserNotCreatedException();
            }
            message = message + " with id= " + userFromKeycloak.getFirst().getId();
        } catch (Exception e) {
            returnStatus = "500";
            message = "User Not Created";
        }

        return new CreateUserResponseDto(returnStatus, message);
    }

    public List<String> createSindico(CreateUserRequestDto user) {
        UserRepresentation userRepresentation = getUserRepresentation(user, RoleEnum.SINDICO);

        String returnStatus = "201";
        String message = "Usuário criado";
        try (Response response = getUsersResource().create(userRepresentation)) {
            List<UserRepresentation> userFromKeycloak = getUsersResource().searchByEmail(user.email(), true);
            System.out.println(response.getStatus());
            if (response.getStatus() != 201 || userFromKeycloak.isEmpty()) {
                throw new UserNotCreatedException();
            }
            message = message + " com id= " + userFromKeycloak.getFirst().getId();
        } catch (Exception e) {
            returnStatus = "500";
            message = "Usuário não criado";
        }

        return List.of(returnStatus, message);
    }

    public ResponseLoginDto loginUser(String email, String password) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("client_id", realm);
        formParams.put("grant_type", "password");
        formParams.put("username", email);
        formParams.put("password", password);

       return loginClient.loginUser(formParams);
    }

    public void logoutUser(String id) {
        keycloak.realm(realm)
                .users().get(id).getUserSessions()
                .forEach(session -> keycloak.realm(realm).deleteSession(session.getId(), false));
    }
}