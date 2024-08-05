package api.condominio.portaria.enums;

import api.condominio.portaria.exceptions.InvalidEnumException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class VehicleCategoryConverter implements AttributeConverter<VehicleCategoryEnum, String> {
    @Override
    public String convertToDatabaseColumn(VehicleCategoryEnum vehicleCategoryEnum) {
        return vehicleCategoryEnum.getValue();
    }

    @Override
    public VehicleCategoryEnum convertToEntityAttribute(String s) {
        return switch (s) {
            case "carro" -> VehicleCategoryEnum.CAR;
            case "moto" -> VehicleCategoryEnum.MOTORCYCLE;
            default -> throw new InvalidEnumException(s);
        };
    }
}