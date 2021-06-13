package org.brahmakumaris.journeyfood.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Constraint(validatedBy = IntegerCountMustBeGreaterThanZeroValidator.class)
@Target({ FIELD, METHOD })
public @interface IntegerCountMustBeGreaterThanZeroConstraint{
	String message() default "Invalid quantity";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}