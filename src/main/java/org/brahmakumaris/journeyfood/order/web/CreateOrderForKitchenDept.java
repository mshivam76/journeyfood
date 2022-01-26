package org.brahmakumaris.journeyfood.order.web;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class CreateOrderForKitchenDept {
	private Long kitchenDeptId;
	@NotNull(message="Order date is mandatory")
    @Future(message = "Order date must be after today's date")
	private LocalDate orderForDate;
	private int chips;
	private int chutney;
	private int tomatoRice;
	private int curdRice;
	private int soojiDhokla;
	private int idli;
	@NotEmpty(message="Order schedule is mandatory")
	private String schedule;
	
	public Long getKitchenDeptId() {
		return kitchenDeptId;
	}
	public void setKitchenDeptId(Long kitchenDeptId) {
		this.kitchenDeptId = kitchenDeptId;
	}
	public LocalDate getOrderForDate() {
		return orderForDate;
	}
	public void setOrderForDate(LocalDate orderForDate) {
		this.orderForDate = orderForDate;
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
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

}