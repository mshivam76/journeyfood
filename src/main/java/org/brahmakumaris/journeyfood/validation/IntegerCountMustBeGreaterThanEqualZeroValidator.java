package org.brahmakumaris.journeyfood.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
//https://www.baeldung.com/spring-mvc-custom-validator
public class IntegerCountMustBeGreaterThanEqualZeroValidator implements ConstraintValidator<IntegerCountMustBeGreaterThanEqualZeroConstraint, Integer> {

	@Override
    public void initialize(IntegerCountMustBeGreaterThanEqualZeroConstraint contactNumber) {
    }

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return (value>=0);
	}
}