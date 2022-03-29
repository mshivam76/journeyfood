package org.brahmakumaris.journeyfood.order.web;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class SubmitFetchTotalQuantityModelByDateForASlot {

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message="Meal Retrieval date is mandatory")
	private LocalDate mealRetrievalDate;
	
	@NotEmpty(message="Choose Food pickup Slot is mandatory")
	private String mealRetrievalTime;

	public String getMealRetrievalTime() {
		return mealRetrievalTime;
	}

	public void setMealRetrievalTime(String mealRetrievalTime) {
		this.mealRetrievalTime = mealRetrievalTime;
	}

	public LocalDate getMealRetrievalDate() {
		return mealRetrievalDate;
	}

	public void setMealRetrievalDate(LocalDate mealRetrievalDate) {
		this.mealRetrievalDate = mealRetrievalDate;
	}
}
