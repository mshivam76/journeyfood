package org.brahmakumaris.journeyfood.order.web;

import javax.validation.constraints.NotBlank;

import org.brahmakumaris.journeyfood.validation.ContactNumberConstraint;

public class UserSignUpFormData {
	@NotBlank(message="Name Of Center is mandatory")
    private String nameOfCenter;
	@NotBlank(message="Name Of Guide/Teacher is mandatory")
    private String nameOfGuide;
	@NotBlank(message="Contact No is is mandatory")
	@ContactNumberConstraint(message = "Invalid Contact number")
    private String contactNoOfGuide;
    @NotBlank(message="Email is mandatory")
    private String email;
    @NotBlank(message="Password is mandatory")
    private String password;
	
	public UserSignUpFormData() {
		super();
	}

	public UserSignUpFormData(@NotBlank(message = "Email is mandatory") String email) {
		super();
		this.email = email;
	}

	public UserCreationParameters toParamsSignUp() {
		return new UserCreationParameters(nameOfCenter, nameOfGuide, contactNoOfGuide, email, password);
	}

	public String getNameOfCenter() {
		return nameOfCenter;
	}

	public void setNameOfCenter(String nameOfCenter) {
		this.nameOfCenter = nameOfCenter;
	}

	public String getNameOfGuide() {
		return nameOfGuide;
	}

	public void setNameOfGuide(String nameOfGuide) {
		this.nameOfGuide = nameOfGuide;
	}

	public String getContactNoOfGuide() {
		return contactNoOfGuide;
	}

	public void setContactNoOfGuide(String contactNoOfGuide) {
		this.contactNoOfGuide = contactNoOfGuide;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
