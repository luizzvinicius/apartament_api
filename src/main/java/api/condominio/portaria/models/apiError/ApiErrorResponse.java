package api.condominio.portaria.models.apiError;

public record ApiErrorResponse(String error, String message) implements IApiError {
}