package api.condominio.portaria.exceptions;

public class InvalidEnumException extends RuntimeException {
    public InvalidEnumException(String value) {
        super(String.format("{error: fail to convert Enum %s }", value));
    }
}