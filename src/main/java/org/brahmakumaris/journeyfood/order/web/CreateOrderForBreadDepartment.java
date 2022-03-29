package org.brahmakumaris.journeyfood.order.web;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class CreateOrderForBreadDepartment {
	private Long breadOrderId;
	@NotNull(message="Order date is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Future(message = "Order date must be after today's date")
	private LocalDate orderForDate;
	private Date orderDate;
	@NotNull(message="Bread Count is mandatory")
	private int bread;
	@NotNull(message="Sliced Bread Count is mandatory")
	private int slicedBread;
	@NotEmpty(message="Food delivery slot is mandatory")
	private String slot;
	
	public CreateOrderForBreadDepartment() {
		super();
		this.orderDate = new Date();
	}
	public Long getBreadOrderId() {
		return breadOrderId;
	}
	public void setBreadOrderId(Long breadOrderId) {
		this.breadOrderId = breadOrderId;
	}
	public LocalDate getOrderForDate() {
		return orderForDate;
	}
	public void setOrderForDate(LocalDate orderForDate) {
		this.orderForDate = orderForDate;
	}
	public int getBread() {
		return bread;
	}
	public void setBread(int bread) {
		this.bread = bread;
	}
	public int getSlicedBread() {
		return slicedBread;
	}
	public void setSlicedBread(int slicedBread) {
		this.slicedBread = slicedBread;
	}
	public String getSlot() {
		return slot;
	}
	public void setSlot(String slot) {
		this.slot = slot;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	@Override
	public String toString() {
		return "CreateOrderForBreadDepartment [breadOrderId=" + breadOrderId + ", orderForDate=" + orderForDate
				+ ", orderDate=" + orderDate + ", bread=" + bread + ", slicedBread=" + slicedBread + ", slot=" + slot
				+ "]";
	}
}