package org.brahmakumaris.journeyfood.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
//https://www.baeldung.com/spring-mvc-custom-validator
public class IntegerCountMustBeGreaterThanZeroValidator implements ConstraintValidator<IntegerCountMustBeGreaterThanZeroConstraint, Integer> {

	@Override
    public void initialize(IntegerCountMustBeGreaterThanZeroConstraint contactNumber) {
    }

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return value>0;
	}
}