package api.condominio.portaria.exceptions;

public class RegisterOverflow extends RuntimeException {
    public RegisterOverflow(int max, String entity) {
        super(String.format("%d %s already registered", max, entity));
    }
}