package validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorBandeira.class)
@Documented
public @interface ValidaBandeira {

	String message() default "Bandeira Inválida";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
