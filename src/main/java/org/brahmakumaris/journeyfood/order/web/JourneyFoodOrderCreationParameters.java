package org.brahmakumaris.journeyfood.order.web;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.springframework.format.annotation.DateTimeFormat;

public class JourneyFoodOrderCreationParameters {
	
    private int headCount;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfOrderPlaced;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfDeparture;
    
    @DateTimeFormat(pattern = "h:mm a")
    private LocalTime mealRetrievalTime;

    private UserEntity user; 
    
    private int thepla;
    
    public Date getDateOfOrderPlaced() {
		return dateOfOrderPlaced;
	}

	public void setDateOfOrderPlaced(Date dateOfOrderPlaced) {
		this.dateOfOrderPlaced = dateOfOrderPlaced;
	}

	private int puri;
    
    private int roti;

    private int achar;
    
    private int jam;
    
    private int bread;
    
    private int others;

	private LocalDate mealRetrievalDate;

    
	public JourneyFoodOrderCreationParameters(int headCount, Date dateOfOrderPlaced, LocalDate dateOfDeparture, LocalDate mealRetrievalDate, LocalTime mealRetrievalTime,
			UserEntity user, int thepla, int puri, int roti, int achar, int jam, int bread, int others) {
		super();
		this.headCount = headCount;
		this.dateOfOrderPlaced = dateOfOrderPlaced;
		this.dateOfDeparture = dateOfDeparture;
		this.mealRetrievalDate = mealRetrievalDate;
		this.mealRetrievalTime = mealRetrievalTime;
		this.user = user;
		this.thepla = thepla;
		this.puri = puri;
		this.roti = roti;
		this.achar = achar;
		this.jam = jam;
		this.bread = bread;
		this.others = others;
	}

	public JourneyFoodOrderCreationParameters() {
		super();
	}

	public int getHeadCount() {
		return headCount;
	}

	public void setHeadCount(int headCount) {
		this.headCount = headCount;
	}

	public LocalDate getDateOfDeparture() {
		return dateOfDeparture;
	}

	public void setDateOfDeparture(LocalDate dateOfDeparture) {
		this.dateOfDeparture = dateOfDeparture;
	}

	public LocalTime getMealRetrievalTime() {
		return mealRetrievalTime;
	}

	public void setMealRetrievalTime(LocalTime mealRetrievalTime) {
		this.mealRetrievalTime = mealRetrievalTime;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
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

	public LocalDate getMealRetrievalDate() {
		return mealRetrievalDate;
	}

	public void setMealRetrievalDate(LocalDate mealRetrievalDate) {
		this.mealRetrievalDate = mealRetrievalDate;
	}
}