package api.condominio.portaria.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String entity) {
        super(String.format("{error: %s NOT FOUND}", entity));
    }
}