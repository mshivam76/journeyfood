package org.brahmakumaris.journeyfood.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrderForRotiDepartment {
	@Id
	private Long rotiDeptId;
	private LocalDate orderForDate;
	private int thepla;
	private int puri;
	private int roti;
	private int sandwich;
	private String schedule;
	
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
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
}