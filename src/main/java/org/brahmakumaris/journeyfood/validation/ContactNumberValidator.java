package org.brahmakumaris.journeyfood.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
//https://www.baeldung.com/spring-mvc-custom-validator
public class ContactNumberValidator implements ConstraintValidator<ContactNumberConstraint, String> {

	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext context) {
		return contactField != null && contactField.matches("[0-9]+")
		          && (contactField.length() > 8) && (contactField.length() < 14);
	}
}