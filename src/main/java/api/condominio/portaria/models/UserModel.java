package api.condominio.portaria.models;

import api.condominio.portaria.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private String userName;

    @Email
    private String email;

    private String password;

    private RoleEnum role;
}