package api.condominio.portaria.validations.placa;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = PlacaValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface PlacaValidation {
    String message() default "Placa no formato inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}