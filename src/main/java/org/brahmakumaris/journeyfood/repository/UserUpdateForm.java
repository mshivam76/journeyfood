package org.brahmakumaris.journeyfood.repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.brahmakumaris.journeyfood.validation.ContactNumberConstraint;

public class UserUpdateForm {

	private long userId;
	@NotBlank(message="Name Of Center is mandatory")
    private String nameOfCenter;
	@NotBlank(message="Name Of Guide/Teacher is mandatory")
    private String nameOfGuide;
	@NotBlank(message="Contact No is is mandatory")
	@ContactNumberConstraint(message = "Invalid Contact number")
    private String contactNoOfGuide;
	@NotBlank(message="Zone name is mandatory")
    private String zone;
    @NotBlank(message="Subzone/City name is mandatory")
    private String subZone;
    @NotNull(message="Pincode/Zipcode is mandatory")
    private int pincode;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
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
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
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
}
