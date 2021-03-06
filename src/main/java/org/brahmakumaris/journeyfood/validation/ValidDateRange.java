package org.brahmakumaris.journeyfood.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Retention(RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
/**
 * Validation annotation to validate that 2 fields have the same value.
 * An array of fields and their matching confirmation fields can be supplied.
 *
 * Example, compare 1 pair of fields:
 * @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
 * 
 * Example, compare more than 1 pair of fields:
 * @FieldMatch.List({
 *   @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
 *   @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")})
 */
public @interface ValidDateRange  {
	String message() default "End Date should be later than Start Date";
	
	Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    
    /**
     * @return The first field
     */
    String startDate();
    /**
     * @return The second field
     */
    String endDate();
}
