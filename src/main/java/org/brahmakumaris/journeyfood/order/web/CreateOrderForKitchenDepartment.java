package org.brahmakumaris.journeyfood.order.web;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class CreateOrderForKitchenDepartment {
	private Long kitchenOrderId;
	@NotNull(message="Order date is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Future(message = "Order date must be after today's date")
	private LocalDate orderForDate;
	private Date orderDate;
	@NotNull(message="Chips Count is mandatory")
	private int chips;
	@NotNull(message="Chutney Count is mandatory")
	private int chutney;
	@NotNull(message="Tomato Rice Count is mandatory")
	private int tomatoRice;
	@NotNull(message="Curd Rice Count is mandatory")
	private int curdRice;
	@NotNull(message="Dhokla(Sooji) Count is mandatory")
	private int soojiDhokla;
	@NotNull(message="Idli Count is mandatory")
	private int idli;
	@NotEmpty(message="Food delivery slot is mandatory")
	private String slot;
	
	public CreateOrderForKitchenDepartment() {
		this.orderDate = new Date();
	}

	public Long getKitchenOrderId() {
		return kitchenOrderId;
	}

	public void setKitchenOrderId(Long kitchenOrderId) {
		this.kitchenOrderId = kitchenOrderId;
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

	public int getChips() {
		return chips;
	}

	public void setChips(int chips) {
		this.chips = chips;
	}

	public int getChutney() {
		return chutney;
	}

	public void setChutney(int chutney) {
		this.chutney = chutney;
	}

	public int getTomatoRice() {
		return tomatoRice;
	}

	public void setTomatoRice(int tomatoRice) {
		this.tomatoRice = tomatoRice;
	}

	public int getCurdRice() {
		return curdRice;
	}

	public void setCurdRice(int curdRice) {
		this.curdRice = curdRice;
	}

	public int getSoojiDhokla() {
		return soojiDhokla;
	}

	public void setSoojiDhokla(int soojiDhokla) {
		this.soojiDhokla = soojiDhokla;
	}

	public int getIdli() {
		return idli;
	}

	public void setIdli(int idli) {
		this.idli = idli;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	@Override
	public String toString() {
		return "CreateOrderForKitchenDepartment [kitchenOrderId=" + kitchenOrderId + ", orderForDate=" + orderForDate
				+ ", orderDate=" + orderDate + ", chips=" + chips + ", chutney=" + chutney + ", tomatoRice="
				+ tomatoRice + ", curdRice=" + curdRice + ", soojiDhokla=" + soojiDhokla + ", idli=" + idli + ", slot="
				+ slot + "]";
	}
}