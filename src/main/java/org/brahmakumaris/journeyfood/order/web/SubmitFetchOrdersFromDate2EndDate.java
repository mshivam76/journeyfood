package org.brahmakumaris.journeyfood.order.web;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.brahmakumaris.journeyfood.validation.ValidDateRange;
import org.springframework.format.annotation.DateTimeFormat;
    @ValidDateRange(startDate = "startDate", endDate = "endDate")
public class SubmitFetchOrdersFromDate2EndDate{
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message="Start date is mandatory")
	private LocalDate startDate;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message="End date is mandatory")
	private LocalDate endDate;
	
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

}