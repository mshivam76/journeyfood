package org.brahmakumaris.journeyfood.order.web;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class JourneyFoodOrderCreationParameters {
	
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

	private long id;
    
    public JourneyFoodOrderCreationParameters(String nameOfCenter, String nameOfGuide, int headCount, String contactNoOfGuide,
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

	public JourneyFoodOrderCreationParameters(long id) {
		// TODO Auto-generated constructor stub
		this.id = id;
	}

	public String getNameOfCenter() {
		return nameOfCenter;
	}

	public String getNameOfGuide() {
		return nameOfGuide;
	}

	public int getHeadCount() {
		return headCount;
	}

	public String getContactNoOfGuide() {
		return contactNoOfGuide;
	}

	public Date getDateOfDeparture() {
		return dateOfDeparture;
	}

	public Date getMealRetrievalTime() {
		return mealRetrievalTime;
	}

	public int getThepla() {
		return thepla;
	}

	public int getPuri() {
		return puri;
	}

	public int getRoti() {
		return roti;
	}

	public int getAchar() {
		return achar;
	}

	public int getJam() {
		return jam;
	}

	public int getBread() {
		return bread;
	}

	public int getOthers() {
		return others;
	}
}