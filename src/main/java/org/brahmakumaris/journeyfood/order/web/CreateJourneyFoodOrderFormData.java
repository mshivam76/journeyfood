package org.brahmakumaris.journeyfood.order.web;


import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.brahmakumaris.journeyfood.validation.IntegerCountMustBeGreaterThanZeroConstraint;
import org.springframework.format.annotation.DateTimeFormat;

public class CreateJourneyFoodOrderFormData {
	private long orderId;
	
	@IntegerCountMustBeGreaterThanZeroConstraint(message="Head count must be greater than 0")
    private int headCount;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private java.util.Date dateOfOrderPlaced;
    
    @NotNull(message="Departure date is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Future(message = "Departure date must be after today's date")
    private LocalDate dateOfDeparture;
    
	@NotNull(message="Meal retrieval Date is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	@Future(message = "Meal retrieval date must be after today's date")
    private LocalDate mealRetrievalDate;
	
	@NotEmpty(message="Food Pickup time is mandatory")
    private String mealRetrievalTime;

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
    
//	@NotBlank(message="Other items is mandatory")
    private String others;
    
    private String orderStatus;
    
	public JourneyFoodOrderCreationParameters toParams() {
		return new JourneyFoodOrderCreationParameters(
				headCount, dateOfOrderPlaced, dateOfDeparture, mealRetrievalDate, mealRetrievalTime, 
				null, thepla, puri, roti, achar, jam, bread, others);
    }
    
	public CreateJourneyFoodOrderFormData() {
		super();
		this.dateOfOrderPlaced =  new java.util.Date();
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public java.util.Date getDateOfOrderPlaced() {
		return dateOfOrderPlaced;
	}

	public void setDateOfOrderPlaced(java.util.Date dateOfOrderPlaced) {
		this.dateOfOrderPlaced = dateOfOrderPlaced;
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

	public String getMealRetrievalTime() {
		return mealRetrievalTime;
	}

	public void setMealRetrievalTime(String mealRetrievalTime) {
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

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDate getMealRetrievalDate() {
		return mealRetrievalDate;
	}

	public void setMealRetrievalDate(LocalDate mealRetrievalDate) {
		this.mealRetrievalDate = mealRetrievalDate;
	}
}
