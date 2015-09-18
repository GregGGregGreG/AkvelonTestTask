package org.akvelontesttask.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
/**
 * Custom Bean Validation constraint.
 * The annotated element must be a String representation of Date in yyyy-MM-dd format, not be null or empty.
 * @author baddev
 *
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PastDateValidator.class)
@Documented
public @interface PastDate {
	
	String message() default "{err.future_date}";

	Class<?>[]groups() default {};

	Class<? extends Payload>[]payload() default {};
}
