package org.brahmakumaris.journeyfood.order.web;

public class UserCreationParameters {
    private String nameOfCenter;
    private String nameOfGuide;
    private String contactNoOfGuide;
    private String email;
    private String password;
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
	public UserCreationParameters(String nameOfCenter, String nameOfGuide, String contactNoOfGuide,
			String email, String password) {
		super();
		this.nameOfCenter = nameOfCenter;
		this.nameOfGuide = nameOfGuide;
		this.contactNoOfGuide = contactNoOfGuide;
		this.email = email;
		this.password = password;
	}
	public UserCreationParameters(String email2, String password2) {
		// TODO Auto-generated constructor stub
		this.email =email2;
		this.password = password2;
	}
	@Override
	public String toString() {
		return "UserCreationParameters [nameOfCenter=" + nameOfCenter + ", nameOfGuide="
				+ nameOfGuide + ", contactNoOfGuide=" + contactNoOfGuide + ", email=" + email + ", password=" + password+ "]";
	}

}
