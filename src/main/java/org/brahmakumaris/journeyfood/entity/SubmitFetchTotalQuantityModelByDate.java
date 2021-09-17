package org.brahmakumaris.journeyfood.entity;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class SubmitFetchTotalQuantityModelByDate{
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message="Meal Retrieval date is mandatory")
	private LocalDate mealRetrievalDate;

	public LocalDate getMealRetrievalDate() {
		return mealRetrievalDate;
	}

	public void setMealRetrievalDate(LocalDate mealRetrievalDate) {
		this.mealRetrievalDate = mealRetrievalDate;
	}
    
	
}