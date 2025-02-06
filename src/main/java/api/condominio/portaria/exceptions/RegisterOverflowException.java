package api.condominio.portaria.exceptions;

public class RegisterOverflowException extends RuntimeException {
    public RegisterOverflowException(int max, String entity) {
        super(String.format("%d %s already registered", max, entity));
    }
}