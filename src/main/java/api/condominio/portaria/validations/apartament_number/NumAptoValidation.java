package api.condominio.portaria.validations.apartament_number;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = NumAptoValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface NumAptoValidation {
    String message() default "Número do apartamento inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}