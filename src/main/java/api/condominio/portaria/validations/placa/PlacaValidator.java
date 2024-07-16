package api.condominio.portaria.validations.placa;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PlacaValidator implements ConstraintValidator<PlacaValidation, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        return Pattern.matches("^[a-z]{3}\\d[a-z0-9]\\d{2}$", s);
    }
}