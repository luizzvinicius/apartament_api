package api.condominio.portaria.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VehicleCategoryEnum {
    CAR("carro"), MOTORCYCLE("moto");

    private final String category;

    VehicleCategoryEnum(String value) {
        this.category = value;
    }

    @JsonValue // Salva vidas
    public String getValue() {
        return category;
    }

    @Override
    public String toString() {
        return category;
    }
}