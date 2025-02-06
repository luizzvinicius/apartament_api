package api.condominio.portaria.exceptions;

public class InvalidEnumException extends RuntimeException {
    public InvalidEnumException(String value) {
        super("fail to convert Enum " + value);
    }
}