package api.condominio.portaria.exceptions;

public class UserNotCreatedException extends RuntimeException {
    public UserNotCreatedException() {
        super("Usuário não criado");
    }
}