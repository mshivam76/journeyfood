package org.brahmakumaris.journeyfood.entity;

import java.time.LocalDate;

import javax.persistence.Id;

public class OrderForKitchenDepartment {
	@Id
	private Long kitchenDeptId;
	private LocalDate orderForDate;
	private int chips;
	private int chutney;
	private int tomatoRice;
	private int curdRice;
	private int soojiDhokla;
	private int idli;
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
