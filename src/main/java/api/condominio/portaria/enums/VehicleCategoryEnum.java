package api.condominio.portaria.enums;

public enum VehicleCategoryEnum {
    CAR("carro"), MOTORCYCLE("moto");

    private String value;

    VehicleCategoryEnum(String category) {
        this.value = category;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}