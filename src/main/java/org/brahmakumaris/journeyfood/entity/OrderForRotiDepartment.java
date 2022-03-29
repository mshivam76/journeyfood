package org.brahmakumaris.journeyfood.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderForRotiDepartment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long rotiDeptId;
	private LocalDate orderForDate;
	private Date orderDate;
	private int thepla;
	private int puri;
	private int roti;
	private int sandwich;
	private String slot;
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
		return "OrderForRotiDepartment [rotiDeptId=" + rotiDeptId + ", orderForDate=" + orderForDate + ", orderDate="
				+ orderDate + ", thepla=" + thepla + ", puri=" + puri + ", roti=" + roti + ", sandwich=" + sandwich
				+ ", slot=" + slot + "]";
	}
}