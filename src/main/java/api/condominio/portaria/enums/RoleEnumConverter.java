package api.condominio.portaria.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleEnumConverter implements AttributeConverter<RoleEnum, String> {
    @Override
    public String convertToDatabaseColumn(RoleEnum roleEnum) {
        return roleEnum.toString();
    }

    @Override
    public RoleEnum convertToEntityAttribute(String s) {
        return switch (s) {
            case "PORTEIRO" -> RoleEnum.PORTEIRO;
            case "SINDICO" -> RoleEnum.SINDICO;
            default -> throw new IllegalArgumentException("Categoria inválido");
        };
    }
}