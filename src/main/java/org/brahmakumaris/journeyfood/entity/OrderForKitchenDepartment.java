package org.brahmakumaris.journeyfood.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderForKitchenDepartment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long kitchenDeptId;
	private LocalDate orderForDate;
	private Date orderDate;
	private int chips;
	private int chutney;
	private int tomatoRice;
	private int curdRice;
	private int soojiDhokla;
	private int idli;
	private String slot;
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
		return "OrderForKitchenDepartment [kitchenDeptId=" + kitchenDeptId + ", orderForDate=" + orderForDate
				+ ", orderDate=" + orderDate + ", chips=" + chips + ", chutney=" + chutney + ", tomatoRice="
				+ tomatoRice + ", curdRice=" + curdRice + ", soojiDhokla=" + soojiDhokla + ", idli=" + idli + ", slot="
				+ slot + "]";
	}
}