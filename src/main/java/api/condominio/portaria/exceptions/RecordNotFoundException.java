package api.condominio.portaria.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String entity) {
        super(entity + " NOT FOUND");
    }
}