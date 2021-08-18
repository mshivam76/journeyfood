package org.brahmakumaris.journeyfood.order.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.brahmakumaris.journeyfood.validation.ContactNumberConstraint;

public class UserSignUpFormData {
	@NotBlank(message="Name Of Center is mandatory")
    private String nameOfCenter;
	@NotBlank(message="Name Of Guide/Teacher is mandatory")
    private String nameOfGuide;
	@NotBlank(message="Contact No is is mandatory")
	@ContactNumberConstraint(message = "Invalid Contact number")
    private String contactNoOfGuide;
    @NotBlank(message="Zone name is mandatory")
    private String zoneName;
    @NotBlank(message="Subzone/City name is mandatory")
    private String subZone;
    @NotNull(message="Pincode/Zipcode is mandatory")
    private int pincode;
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

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getSubZone() {
		return subZone;
	}

	public void setSubZone(String subZone) {
		this.subZone = subZone;
	}
	
    public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
