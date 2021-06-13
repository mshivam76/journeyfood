package org.brahmakumaris.journeyfood.order.web;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.brahmakumaris.journeyfood.entity.JourneyFoodOrderCreationParameters;
import org.brahmakumaris.journeyfood.validation.ContactNumberConstraint;
import org.brahmakumaris.journeyfood.validation.IntegerCountMustBeGreaterThanZeroConstraint;
import org.springframework.format.annotation.DateTimeFormat;

public class CreateJourneyFoodOrderFormData {
	
	@NotBlank(message="Name Of Center is mandatory")
    private String nameOfCenter;

    @NotBlank(message="Name Of Guide is mandatory")
    private String nameOfGuide;
    
    @IntegerCountMustBeGreaterThanZeroConstraint(message="Head count must be greater than 0")
    private int headCount;
    
    @NotEmpty(message="Mobile No Of Guide is mandatory")
    @ContactNumberConstraint(message = "Invalid Mobile number length")
    private String contactNoOfGuide;
    
    @NotNull(message="Departure date is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Future(message = "Entered date must be after today's date")
    private Date dateOfDeparture;
    
	@NotNull(message="Meal retrieval time is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy h:mm a")
	@Future(message = "Entered date must be after today's date")
    private Date mealRetrievalTime;

    @NotNull(message="Thepla Count is mandatory")
    private int thepla;
    
    @NotNull(message="Puri Count is mandatory")
    private int puri;
    
    @NotNull(message="Roti Count is mandatory")
    private int roti;

    @NotNull(message="Achar count is mandatory")
    private int achar;
    
    @NotNull(message="Jam count is mandatory")
    private int jam;
    
	@NotNull(message="Bread count is mandatory")
    private int bread;
    
    @NotNull(message="Other items count is mandatory")
    private int others;
    
	public JourneyFoodOrderCreationParameters toParams() {
		return new JourneyFoodOrderCreationParameters(nameOfCenter, nameOfGuide, headCount, contactNoOfGuide, dateOfDeparture, mealRetrievalTime, thepla, puri, roti, achar, jam, bread, others);
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

	public int getHeadCount() {
		return headCount;
	}

	public void setHeadCount(int headCount) {
		this.headCount = headCount;
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

	public int getThepla() {
		return thepla;
	}

	public void setThepla(int thepla) {
		this.thepla = thepla;
	}

	public int getPuri() {
		return puri;
	}

	public void setPuri(int puri) {
		this.puri = puri;
	}

	public int getRoti() {
		return roti;
	}

	public void setRoti(int roti) {
		this.roti = roti;
	}

	public int getAchar() {
		return achar;
	}

	public void setAchar(int achar) {
		this.achar = achar;
	}

	public int getJam() {
		return jam;
	}

	public void setJam(int jam) {
		this.jam = jam;
	}

	public int getBread() {
		return bread;
	}

	public void setBread(int bread) {
		this.bread = bread;
	}

	public int getOthers() {
		return others;
	}

	public void setOthers(int others) {
		this.others = others;
	}
    
    

}
