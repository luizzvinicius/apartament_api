package api.condominio.portaria.validations.apartament_number;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class BlocoValidator implements ConstraintValidator<BlocoValidation, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.matches("^(?:[1-9]|1\\d|2\\d)$", s);
    }
}