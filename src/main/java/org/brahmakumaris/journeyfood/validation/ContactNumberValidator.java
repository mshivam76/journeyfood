package org.brahmakumaris.journeyfood.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
//https://www.baeldung.com/spring-mvc-custom-validator
public class ContactNumberValidator implements ConstraintValidator<ContactNoConstraint, String> {

	@Override
    public void initialize(ContactNoConstraint contactNumber) {
    }
	
	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return contactField != null && contactField.matches("[0-9]+")
		          && (contactField.length() > 8) && (contactField.length() < 14);
	}

}
