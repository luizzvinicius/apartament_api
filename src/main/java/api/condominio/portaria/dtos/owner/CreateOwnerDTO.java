package api.condominio.portaria.dtos.owner;

public record CreateOwnerDTO (
        String bloco,
        String numApto,
        String name,
        String cpf,
        String phone
) { }