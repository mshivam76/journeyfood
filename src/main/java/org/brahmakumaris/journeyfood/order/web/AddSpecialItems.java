package org.brahmakumaris.journeyfood.order.web;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class AddSpecialItems {
	
	private Long id;
	
	@NotEmpty(message="Special Item is mandatory")
	private String specialItems;

	@NotNull(message="Item prepration date is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	@Future(message = "Item prepration date must be after today's date")
    private LocalDate servingDate;
	
	@NotEmpty(message="Item serving slot is mandatory")
    private String servingSlot;

	private Date dateOfOrder;
	
	public AddSpecialItems() {
		super();
		this.dateOfOrder = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpecialItems() {
		return specialItems;
	}

	public void setSpecialItems(String specialItems) {
		this.specialItems = specialItems;
	}

	public LocalDate getServingDate() {
		return servingDate;
	}

	public void setServingDate(LocalDate servingDate) {
		this.servingDate = servingDate;
	}

	public String getServingSlot() {
		return servingSlot;
	}

	public void setServingSlot(String servingSlot) {
		this.servingSlot = servingSlot;
	}
	
	public Date getDateOfOrder() {
		return dateOfOrder;
	}

	public void setDateOfOrder(Date dateOfOrder) {
		this.dateOfOrder = new Date();
	}

	@Override
	public String toString() {
		return "AddSpecialItems [specialItems=" + specialItems + ", servingDate=" + servingDate + ", servingSlot="
				+ servingSlot + ", dateOfOrder=" + dateOfOrder + "]";
	}
}