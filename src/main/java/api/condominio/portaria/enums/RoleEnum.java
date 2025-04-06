package api.condominio.portaria.enums;

public enum RoleEnum {
    SINDICO("SINDICO"), PORTEIRO("PORTEIRO");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}