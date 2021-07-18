package org.brahmakumaris.journeyfood.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class JourneyFoodOrder{

	@Override
	public String toString() {
		return "JourneyFoodOrder [id=" + id + ", nameOfCenter=" + nameOfCenter + ", nameOfGuide=" + nameOfGuide
				+ ", headCount=" + headCount + ", contactNoOfGuide=" + contactNoOfGuide + ", dateOfDeparture="
				+ dateOfDeparture + ", mealRetrievalTime=" + mealRetrievalTime + ", thepla=" + thepla + ", puri=" + puri
				+ ", roti=" + roti + ", achar=" + achar + ", jam=" + jam + ", bread=" + bread + ", others=" + others
				+ "]";
	}

	public JourneyFoodOrder() {
		super();
	}
	
	public JourneyFoodOrder(long id) {
		this.id = id;
	}

	public JourneyFoodOrder(String nameOfCenter, String nameOfGuide, int headCount, String contactNoOfGuide,
			Date dateOfDeparture, Date mealRetrievalTime, int thepla, int puri, int roti, int achar, int jam, int bread,
			int others) {
		super();
		this.nameOfCenter = nameOfCenter;
		this.nameOfGuide = nameOfGuide;
		this.headCount = headCount;
		this.contactNoOfGuide = contactNoOfGuide;
		this.dateOfDeparture = dateOfDeparture;
		this.mealRetrievalTime = mealRetrievalTime;
		this.thepla = thepla;
		this.puri = puri;
		this.roti = roti;
		this.achar = achar;
		this.jam = jam;
		this.bread = bread;
		this.others = others;
	}


	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
    private String nameOfCenter;

    private String nameOfGuide;
    
    private int headCount;
    
    private String contactNoOfGuide;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfDeparture;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy h:mm a")
    private Date mealRetrievalTime;

    private int thepla;
    
    private int puri;
    
    private int roti;

    private int achar;
    
    private int jam;
    
    private int bread;
    
    private int others;
    
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNameOfCenter() {
		return nameOfCenter;
	}

	public void setNameOfCenter(String nameOfCenter) {
		this.nameOfCenter = nameOfCenter;
	}

	public int getHeadCount() {
		return headCount;
	}

	public void setHeadCount(int headCount) {
		this.headCount = headCount;
	}

	public String getNameOfGuide() {
		return nameOfGuide;
	}

	public void setNameOfGuide(String nameOfGuide) {
		this.nameOfGuide = nameOfGuide;
	}

	public String getContactNoOfGuide() {
		return contactNoOfGuide;
	}

	public void setContactNoOfGuide(String contactNoOfGuide) {
		this.contactNoOfGuide = contactNoOfGuide;
	}

	public Date getDateOfDeparture() {
		return dateOfDeparture;
	}

	public void setDateOfDeparture(Date dateOfDeparture) {
		this.dateOfDeparture = dateOfDeparture;
	}

	public Date getMealRetrievalTime() {
		return mealRetrievalTime;
	}

	public void setMealRetrievalTime(Date mealRetrievalTime) {
		this.mealRetrievalTime = mealRetrievalTime;
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

	public int getAchar() {
		return achar;
	}

	public void setAchar(int achar) {
		this.achar = achar;
	}

	public int getOthers() {
		return others;
	}

	public void setOthers(int others) {
		this.others = others;
	}
	
    public int getJam() {
		return jam;
	}

	public void setJam(int jam) {
		this.jam = jam;
	}

	public int getBread() {
		return bread;
	}

	public void setBread(int bread) {
		this.bread = bread;
	}
}