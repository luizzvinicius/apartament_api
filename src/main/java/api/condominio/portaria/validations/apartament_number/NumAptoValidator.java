package api.condominio.portaria.validations.apartament_number;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class NumAptoValidator implements ConstraintValidator<NumAptoValidation, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        return Pattern.matches("^[0-3]0[1-4]$", s);
    }
}