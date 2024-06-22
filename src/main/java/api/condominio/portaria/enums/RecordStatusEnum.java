package api.condominio.portaria.enums;

public enum RecordStatusEnum {
    ACTIVE("ativo"), INACTIVE("inativo");

    private final String status;

    RecordStatusEnum(String value) {
        this.status = value;
    }

    public String getValue() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }
}