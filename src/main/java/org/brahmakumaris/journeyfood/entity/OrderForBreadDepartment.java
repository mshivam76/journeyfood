package org.brahmakumaris.journeyfood.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderForBreadDepartment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long breadOrderId;
	private LocalDate orderForDate;
	private Date orderDate; 
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
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	@Override
	public String toString() {
		return "OrderForBreadDepartment [breadOrderId=" + breadOrderId + ", orderForDate=" + orderForDate
				+ ", orderDate=" + orderDate + ", bread=" + bread + ", slicedBread=" + slicedBread + ", slot=" + slot
				+ "]";
	}
}