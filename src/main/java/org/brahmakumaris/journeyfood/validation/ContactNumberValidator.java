package org.brahmakumaris.journeyfood.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
//https://www.baeldung.com/spring-mvc-custom-validator
public class ContactNumberValidator implements ConstraintValidator<ContactNumberConstraint, String> {

	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext context) {
		if(contactField == null){
			return false;
		}
		//validate phone numbers of format "1234567890"
        if (contactField.matches("\\d{10}")) return true;
        //validating phone number with -, . or spaces
//        else if(contactField.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
//        //validating phone number with extension length from 3 to 5
//        else if(contactField.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
//        //validating phone number where area code is in braces ()
//        else if(contactField.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
//        //return false if nothing matches the input
        else return false;
	}
}