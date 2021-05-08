package org.brahmakumaris.journeyfood.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

public class JourneyFoodOrder{
	/*
	 *
	 * id	
	Date of Journey-
	Name of Bro/Sis
	Name of Center
	Guide/Teacher name
	Mobileno of Bro/Sis
	Contactno of  Guide/Teacher
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

	@NotBlank(message = "name Of Center is mandatory")
    private String nameOfCenter;

    @NotBlank(message = "Name Of Guide is mandatory")
    private String nameOfGuide;
    
    @NotBlank(message = "Head Count is mandatory")
    private int headCount;
    
    @NotBlank(message = "Mobile No Of Guide is mandatory")
    private String contactNoOfGuide;
    
    @NotBlank(message = "Date of Journey is mandatory")
    private Date dateOfDeparture;
    
    @NotBlank(message = "Journey meal retrival date and time is mandatory")
    private Date mealRetrievalTime;

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNameOfCenter() {
		return nameOfCenter;
	}

	public void setNameOfCenter(String nameOfCenter) {
		this.nameOfCenter = nameOfCenter;
	}

	public int getHeadCount() {
		return headCount;
	}

	public void setHeadCount(int headCount) {
		this.headCount = headCount;
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

	public Date getDateOfDeparture() {
		return dateOfDeparture;
	}

	public void setDateOfDeparture(Date dateOfDeparture) {
		this.dateOfDeparture = dateOfDeparture;
	}

	public Date getMealRetrievalTime() {
		return mealRetrievalTime;
	}

	public void setMealRetrievalTime(Date mealRetrievalTime) {
		this.mealRetrievalTime = mealRetrievalTime;
	}
}