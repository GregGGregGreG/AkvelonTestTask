package org.akvelontesttask.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.akvelontesttask.domain.PersonInfo;
import org.akvelontesttask.validation.PastDate;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * DTO for personInfo form. Has validation rules.
 * @author baddev
 *
 */
public class PersonInfoForm {
	
	/*group #1
	   (19|20)\\d\\d #	19[0-9][0-9] or 20[0-9][0-9]
	   - # "-"
	  group #2
	   0?[1-9] # 01-09 or 1-9 | # ..or 1[012] # 10,11,12
	   - # "-"
	  group #3
	   0?[1-9] # 01-09 or 1-9 | # ..or [12][0-9] # 10-19 or 20-29 | #  ..or 3[01] # 30, 31
	*/
	public static final String DATE_PATTERN = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";

	@NotEmpty(message="{err.empty}")
	@Size(max=45, min=2, message="{err.name_len}")
	@Pattern(regexp="[A-Z][a-z]*", message="{err.name_pat}")
	private String firstName;

	@NotEmpty(message="{err.empty}")
	@Size(max=45, min=2, message="{err.name_len}")
	@Pattern(regexp="[A-Z][a-z]*", message="{err.name_pat}")
	private String lastName;
	
	@NotNull(message="{err.empty}")
	@Range(min=1, max=150, message="{err.age}")
	private Integer age;

	@NotEmpty(message="{err.empty}")
	@Pattern(regexp = DATE_PATTERN, message="{err.inv_date}")
	@PastDate
	private String birthDate;

	@NotNull(message="{err.empty_gender}")
	private Character gender;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}
	
	public PersonInfo makePersonInfo(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse(this.birthDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new PersonInfo.Builder(this.firstName, this.lastName)
				.age(this.age)
				.gender(this.gender)
				.birthDate(date)
			.build();
	}

	@Override
	public String toString() {
		return "PersonInformation [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", birthDate=" + birthDate.toString() + ", gender=" + gender + "]";
	}

}
