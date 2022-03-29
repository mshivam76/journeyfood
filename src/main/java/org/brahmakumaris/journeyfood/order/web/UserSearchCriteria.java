package org.brahmakumaris.journeyfood.order.web;

import java.util.Date;

public class UserSearchCriteria {
	private String nameOfCenter;
	private String nameOfGuide;
	private String contactNoOfGuide;
	private String email;
	private String zone;
	private String subZone;
	private Integer pincode;
	private Date dateCreated;
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
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
