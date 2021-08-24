package org.brahmakumaris.journeyfood.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.brahmakumaris.journeyfood.controller.HomeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class JourneyFoodOrder{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
    private int headCount;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfOrderPlaced;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfDeparture;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy h:mm a")
    private Date mealRetrievalTime;

    @ManyToOne
    private UserEntity user; 
    
    private int thepla;
    
    private int puri;
    
    private int roti;

    private int achar;
    
    private int jam;
    
    private int bread;
    
    private int others;

    public JourneyFoodOrder() {
		super();
	}
    
	public JourneyFoodOrder(long id, int headCount, Date dateOfOrderPlaced, Date dateOfDeparture,
			Date mealRetrievalTime, UserEntity user, int thepla, int puri, int roti, int achar, int jam, int bread,
			int others) {
		super();
		this.id = id;
		this.headCount = headCount;
		this.dateOfOrderPlaced = dateOfOrderPlaced;
		this.dateOfDeparture = dateOfDeparture;
		this.mealRetrievalTime = mealRetrievalTime;
		this.user = user;
		this.thepla = thepla;
		this.puri = puri;
		this.roti = roti;
		this.achar = achar;
		this.jam = jam;
		this.bread = bread;
		this.others = others;
	}

	public JourneyFoodOrder( int headCount, Date dateOfOrderPlaced, Date dateOfDeparture,
			Date mealRetrievalTime, UserEntity user, int thepla, int puri, int roti, int achar, int jam, int bread,
			int others) {
		super();
		this.headCount = headCount;
		this.dateOfOrderPlaced = dateOfOrderPlaced;
		this.dateOfDeparture = dateOfDeparture;
		this.mealRetrievalTime = mealRetrievalTime;
		this.user = user;
		this.thepla = thepla;
		this.puri = puri;
		this.roti = roti;
		this.achar = achar;
		this.jam = jam;
		this.bread = bread;
		this.others = others;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getHeadCount() {
		return headCount;
	}

	public void setHeadCount(int headCount) {
		this.headCount = headCount;
	}

	public Date getDateOfOrderPlaced() {
		return dateOfOrderPlaced;
	}

	public void setDateOfOrderPlaced(Date dateOfOrderPlaced) {
		this.dateOfOrderPlaced = dateOfOrderPlaced;
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

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
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

	public int getOthers() {
		return others;
	}

	public void setOthers(int others) {
		this.others = others;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JourneyFoodOrder other = (JourneyFoodOrder) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	

}