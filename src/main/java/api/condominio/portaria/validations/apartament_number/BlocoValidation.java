package api.condominio.portaria.validations.apartament_number;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = BlocoValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface BlocoValidation {
    String message() default "Número do bloco inválido, aceito de 1 a 29)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}