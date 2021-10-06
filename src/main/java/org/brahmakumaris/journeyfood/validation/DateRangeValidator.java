package org.brahmakumaris.journeyfood.validation;

import java.lang.reflect.Method;
import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.brahmakumaris.journeyfood.order.web.SubmitFetchOrdersFromDate2EndDate;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object>{
    private String startDate;
    private String endDate;
    
    public void initialize(ValidDateRange validateDateRange) {
    	startDate = validateDateRange.startDate();
    	endDate =  validateDateRange.endDate();
    }
	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		try {
            Class<? extends Object> clazz = object.getClass();
            LocalDate startDate = null;
            Method startGetter = clazz.getMethod(getAccessorMethodName(this.startDate), new Class[0]);
            Object startGetterResult = startGetter.invoke(object, null);
            if (startGetterResult != null && startGetterResult instanceof LocalDate){
                startDate = (LocalDate) startGetterResult;
            }else{
                return false;
            }
            LocalDate endDate = null;
            Method endGetter = clazz.getMethod(getAccessorMethodName(this.endDate), new Class[0]);
            Object endGetterResult = endGetter.invoke(object, null);
            if (endGetterResult == null){
                return true;
            }
            if (endGetterResult instanceof LocalDate){
                endDate = (LocalDate) endGetterResult;
            }
            return (startDate.isBefore(endDate));           
        } catch (Throwable e) {
            System.err.println(e);
        }

        return false;
	}
	private String getAccessorMethodName(String property){
        StringBuilder builder = new StringBuilder("get");
        builder.append(Character.toUpperCase(property.charAt(0))); 
        builder.append(property.substring(1));
        return builder.toString();
    }

}
