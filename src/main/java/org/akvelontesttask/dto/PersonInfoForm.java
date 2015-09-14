package org.akvelontesttask.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.akvelontesttask.domain.PersonInfo;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * DTO for personInfo form. Has validation rules.
 * @author baddev
 *
 */

public class PersonInfoForm {

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

	@NotNull(message="{err.empty_date}")
	@Past(message="{err.future_date}")
	@Temporal(TemporalType.DATE)
	//handles data-binding (parsing) and display if spring form tld or spring:eval
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthDate;

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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}
	
	public PersonInfo makePersonInfo(){
		return new PersonInfo.Builder(this.firstName, this.lastName)
				.age(this.age)
				.gender(this.gender)
				.birthDate(this.birthDate)
			.build();
	}

	@Override
	public String toString() {
		return "PersonInformation [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", birthDate=" + birthDate.toString() + ", gender=" + gender + "]";
	}

}
