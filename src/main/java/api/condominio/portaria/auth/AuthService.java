package api.condominio.portaria.auth;

import api.condominio.portaria.dtos.user.CreateUserDto;
import api.condominio.portaria.enums.RoleEnum;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.UserSessionRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    private final Keycloak keycloak;
    @Value("${app.keycloak.realm}")
    private String realm;

    public AuthService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    private UserRepresentation getUserRepresentation(CreateUserDto user, RoleEnum role) {
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

    public List<String> createPorteiro(CreateUserDto user) {
        UserRepresentation userRepresentation = getUserRepresentation(user, RoleEnum.PORTEIRO);

        String returnStatus = "201";
        String message = "Usuário criado";
        try (Response response = getUsersResource().create(userRepresentation)) {
            List<UserRepresentation> userFromKeycloak = getUsersResource().searchByEmail(user.email(), true);
            System.out.println(response.getStatus());
            if (response.getStatus() != 201 || userFromKeycloak.isEmpty()) {
                throw new RuntimeException();
            }
            message = message + " com id= " + userFromKeycloak.getFirst().getId();
        } catch (Exception e) {
            returnStatus = "500";
            message = "Usuário não criado";
        }

        return List.of(returnStatus, message);
    }

    public List<String> createSindico(CreateUserDto user) {
        UserRepresentation userRepresentation = getUserRepresentation(user, RoleEnum.SINDICO);

        String returnStatus = "201";
        String message = "Usuário criado";
        try (Response response = getUsersResource().create(userRepresentation)) {
            List<UserRepresentation> userFromKeycloak = getUsersResource().searchByEmail(user.email(), true);
            System.out.println(response.getStatus());
            if (response.getStatus() != 201 || userFromKeycloak.isEmpty()) {
                throw new RuntimeException();
            }
            message = message + " com id= " + userFromKeycloak.getFirst().getId();
        } catch (Exception e) {
            returnStatus = "500";
            message = "Usuário não criado";
        }

        return List.of(returnStatus, message);
    }

    public void logoutUser(String id) {
        keycloak.realm(realm)
                .users().get(id).getUserSessions().stream()
                .map(UserSessionRepresentation::getId)
                .forEach(s -> keycloak.realm(realm).deleteSession(s, false));
    }
}