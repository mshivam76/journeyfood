package org.brahmakumaris.journeyfood.order.web;


import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class PreOrderFormData {
    
    @NotNull(message="Departure date is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Future(message = "Departure date must be after today's date")
    private LocalDate dateOfDeparture;
    
	@NotNull(message="Meal retrieval Date is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	@Future(message = "Meal retrieval date must be after today's date")
    private LocalDate mealRetrievalDate;
	
	@NotEmpty(message="Food Pickup slot is mandatory")
    private String mealRetrievalTime;

	public PreOrderFormData() {
		super();
	}

	public LocalDate getDateOfDeparture() {
		return dateOfDeparture;
	}

	public void setDateOfDeparture(LocalDate dateOfDeparture) {
		this.dateOfDeparture = dateOfDeparture;
	}

	public LocalDate getMealRetrievalDate() {
		return mealRetrievalDate;
	}

	public void setMealRetrievalDate(LocalDate mealRetrievalDate) {
		this.mealRetrievalDate = mealRetrievalDate;
	}

	public String getMealRetrievalTime() {
		return mealRetrievalTime;
	}

	public void setMealRetrievalTime(String mealRetrievalTime) {
		this.mealRetrievalTime = mealRetrievalTime;
	}

	@Override
	public String toString() {
		return "PreOrderFormData [dateOfDeparture=" + dateOfDeparture + ", mealRetrievalDate=" + mealRetrievalDate
				+ ", mealRetrievalTime=" + mealRetrievalTime + "]";
	}
	
}
