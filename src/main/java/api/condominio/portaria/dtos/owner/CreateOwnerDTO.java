package api.condominio.portaria.dtos.owner;

public record CreateOwnerDTO (
        String name,
        String cpf,
        String phone) {
}