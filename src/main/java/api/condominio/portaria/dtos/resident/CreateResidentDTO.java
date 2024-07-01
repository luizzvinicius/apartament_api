package api.condominio.portaria.dtos.resident;

public record CreateResidentDTO(
        String bloco,
        String numApto,
        String name,
        String cpf,
        String phone
) { }