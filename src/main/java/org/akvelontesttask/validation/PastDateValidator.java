package org.akvelontesttask.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.akvelontesttask.dto.PersonInfoForm;

/**
 * Validator which ensures that given Date is a past value.
 * @author baddev
 *
 */

public class PastDateValidator implements ConstraintValidator<PastDate, String>{

	private Date now;
	
	@Override
	public void initialize(PastDate constraintAnnotation) {
		this.now = new Date();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		//date must have valid format
		if(value==null || value.isEmpty() || !value.matches(PersonInfoForm.DATE_PATTERN)){
			return true;
		}
		
		Date given = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			given = df.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(given.after(now)){
			return false;
		}
		
		return true;
	}

}
