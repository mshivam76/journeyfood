package org.brahmakumaris.journeyfood.entity;

import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class OrderForBreadDepartment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long breadOrderId;
	private LocalDate orderForDate;
	private int bread;
	private int slicedBread;
	private String slot;
	
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
	@Override
	public String toString() {
		return "OrderForBreadDepartment [breadOrderId=" + breadOrderId + ", orderForDate=" + orderForDate + ", bread="
				+ bread + ", slicedBread=" + slicedBread + ", slot=" + slot + "]";
	}
}