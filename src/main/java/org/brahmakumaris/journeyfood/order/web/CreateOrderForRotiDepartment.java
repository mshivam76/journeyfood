package org.brahmakumaris.journeyfood.order.web;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class CreateOrderForRotiDepartment {
	private Long rotiDeptId;
	@NotNull(message="Order date is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Future(message = "Order date must be after today's date")
	private LocalDate orderForDate;
	private Date orderDate;
	@NotNull(message="Thepla Count is mandatory")
	private int thepla;
	@NotNull(message="Puri Count is mandatory")
	private int puri;
	@NotNull(message="Roti Count is mandatory")
	private int roti;
	@NotNull(message="Sandwich Count is mandatory")
	private int sandwich;
	@NotEmpty(message="Food delivery slot is mandatory")
	private String slot;
	
	public CreateOrderForRotiDepartment() {
		super();
		this.orderDate = new Date();
	}

	public Long getRotiDeptId() {
		return rotiDeptId;
	}

	public void setRotiDeptId(Long rotiDeptId) {
		this.rotiDeptId = rotiDeptId;
	}

	public LocalDate getOrderForDate() {
		return orderForDate;
	}

	public void setOrderForDate(LocalDate orderForDate) {
		this.orderForDate = orderForDate;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
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

	public int getSandwich() {
		return sandwich;
	}

	public void setSandwich(int sandwich) {
		this.sandwich = sandwich;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	@Override
	public String toString() {
		return "CreateOrderForRotiDepartment [rotiDeptId=" + rotiDeptId + ", orderForDate=" + orderForDate
				+ ", orderDate=" + orderDate + ", thepla=" + thepla + ", puri=" + puri + ", roti=" + roti
				+ ", sandwich=" + sandwich + ", slot=" + slot + "]";
	}
}