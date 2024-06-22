package api.condominio.portaria.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RecordStatusConverter implements AttributeConverter<RecordStatusEnum, String> {
    @Override
    public String convertToDatabaseColumn(RecordStatusEnum recordStatus) {
        return recordStatus.getValue();
    }

    @Override
    public RecordStatusEnum convertToEntityAttribute(String s) {
        return switch (s) {
            case "ativo" -> RecordStatusEnum.ACTIVE;
            case "inativo" -> RecordStatusEnum.INACTIVE;
            default -> throw new IllegalArgumentException("Status inválido");
        };
    }
}