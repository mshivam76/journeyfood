package org.brahmakumaris.journeyfood.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class SpecialItemForADate{
	@Id
	@Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String specialItems;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfOrder;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate servingDate;

	private String servingSlot;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpecialItems() {
		return specialItems;
	}

	public void setSpecialItems(String specialItems) {
		this.specialItems = specialItems;
	}

	public LocalDate getServingDate() {
		return servingDate;
	}

	public void setServingDate(LocalDate servingDate) {
		this.servingDate = servingDate;
	}

	public String getServingSlot() {
		return servingSlot;
	}

	public void setServingSlot(String servingSlot) {
		this.servingSlot = servingSlot;
	}
	

    public Date getDateOfOrder() {
		return dateOfOrder;
	}

	public void setDateOfOrder(Date dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}
}
