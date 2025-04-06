package api.condominio.portaria.controller.auth;

import api.condominio.portaria.enums.RoleEnum;
import api.condominio.portaria.models.UserModel;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.UserSessionRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final Keycloak keycloak;
    @Value("${app.keycloak.realm}")
    private String realm;

    public UserController(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserModel user) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(user.getUserName());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(user.getPassword());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        userRepresentation.setGroups(List.of(RoleEnum.PORTEIRO.toString() + "S"));
        userRepresentation.setCredentials(List.of(credentialRepresentation));

        int returnStatus = 201;
        String message = "Usuário criado";
        try (Response response = getUsersResource().create(userRepresentation)) {
            List<UserRepresentation> userFromKeycloak = getUsersResource().searchByEmail(user.getEmail(), true);
            System.out.println(response.getStatus());
            if (response.getStatus() != 201 || userFromKeycloak.isEmpty()) {
                throw new RuntimeException();
            }
            message = message + " com id= " + userFromKeycloak.getFirst().getId();
        } catch (Exception e) {
            System.out.println(e);
            returnStatus = 500;
            message = "Usuário não criado";
        }
        return ResponseEntity.status(returnStatus).body(message);
    }

    @PostMapping("/logout/{id}")
    public ResponseEntity<Void> logoutUser(@PathVariable String id) {
        Stream<String> sessions = keycloak.realm(realm)
                .users()
                .get(id)
                .getUserSessions()
                .stream()
                .map(UserSessionRepresentation::getId);

        sessions.forEach(s -> keycloak.realm(realm).deleteSession(s, false));
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/sendmail/{userId}")
//    public void sendVerificationEmail(@PathVariable String userId) {
//        var user = getUsersResource().get(userId);
//        System.out.println(user.toString());
//        System.out.println(user.toRepresentation().toString());
//        user.sendVerifyEmail();
//    }

    // delete por id

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }
}