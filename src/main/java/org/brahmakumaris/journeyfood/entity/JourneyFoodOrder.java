package org.brahmakumaris.journeyfood.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class JourneyFoodOrder{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
	
    private Integer headCount;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfOrderPlaced;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfDeparture;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate mealRetrievalDate;
    
    @DateTimeFormat(pattern = "hh:mm:ss a")
    private LocalTime mealRetrievalTime;

    @ManyToOne
    private UserEntity user; 
    
    private Integer thepla;
    
    private Integer puri;
    
    private Integer roti;

    private Integer achar;
    
    private Integer jam;
    
    private Integer bread;
    
    private Integer others;
    
    private String orderStatus;

	public JourneyFoodOrder() {
		super();
	}
 
	public JourneyFoodOrder(Long orderId, Integer headCount, Date dateOfOrderPlaced, LocalDate dateOfDeparture, LocalDate mealRetrievalDate,
			LocalTime mealRetrievalTime, String orderStatus, Integer thepla, Integer puri, Integer roti, Integer achar, Integer jam, Integer bread,
			Integer others) {
		super();
		this.orderId = orderId;
		this.headCount = headCount;
		this.dateOfOrderPlaced = dateOfOrderPlaced;
		this.dateOfDeparture = dateOfDeparture;
		this.mealRetrievalDate = mealRetrievalDate;
		this.mealRetrievalTime = mealRetrievalTime;
		this.thepla = thepla;
		this.puri = puri;
		this.roti = roti;
		this.achar = achar;
		this.jam = jam;
		this.bread = bread;
		this.others = others;
		this.orderStatus = orderStatus;
	}
	
	public JourneyFoodOrder(Long orderId, Integer headCount, Date dateOfOrderPlaced, LocalDate dateOfDeparture, LocalDate mealRetrievalDate, 
			LocalTime mealRetrievalTime, UserEntity user, String orderStatus, Integer others, Integer thepla, Integer puri, Integer roti, Integer achar, 
			Integer jam, Integer bread) {
		super();
		this.orderId = orderId;
		this.headCount = headCount;
		this.dateOfOrderPlaced = dateOfOrderPlaced;
		this.dateOfDeparture = dateOfDeparture;
		this.mealRetrievalDate = mealRetrievalDate;
		this.mealRetrievalTime = mealRetrievalTime;
		this.user = user;
		this.thepla = thepla;
		this.puri = puri;
		this.roti = roti;
		this.achar = achar;
		this.jam = jam;
		this.bread = bread;
		this.others = others;
		this.orderStatus = orderStatus;
	}

	public JourneyFoodOrder( Integer headCount, Date dateOfOrderPlaced, LocalDate dateOfDeparture, LocalDate mealRetrievalDate,
			LocalTime mealRetrievalTime, UserEntity user, String orderStatus, Integer thepla, Integer puri, Integer roti, Integer achar, Integer jam, Integer bread,
			Integer others) {
		super();
		this.headCount = headCount;
		this.dateOfOrderPlaced = dateOfOrderPlaced;
		this.dateOfDeparture = dateOfDeparture;
		this.mealRetrievalDate = mealRetrievalDate;
		this.mealRetrievalTime = mealRetrievalTime;
		this.user = user;
		this.orderStatus =orderStatus;
		this.thepla = thepla;
		this.puri = puri;
		this.roti = roti;
		this.achar = achar;
		this.jam = jam;
		this.bread = bread;
		this.others = others;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getHeadCount() {
		return headCount;
	}

	public void setHeadCount(Integer headCount) {
		this.headCount = headCount;
	}

	public Date getDateOfOrderPlaced() {
		return dateOfOrderPlaced;
	}

	public void setDateOfOrderPlaced(Date dateOfOrderPlaced) {
		this.dateOfOrderPlaced = dateOfOrderPlaced;
	}

	public LocalDate getDateOfDeparture() {
		return dateOfDeparture;
	}

	public void setDateOfDeparture(LocalDate dateOfDeparture) {
		this.dateOfDeparture = dateOfDeparture;
	}

	public LocalTime getMealRetrievalTime() {
		return mealRetrievalTime;
	}

	public void setMealRetrievalTime(LocalTime mealRetrievalTime) {
		this.mealRetrievalTime = mealRetrievalTime;
	}
	
	public LocalDate getMealRetrievalDate() {
		return mealRetrievalDate;
	}

	public void setMealRetrievalDate(LocalDate mealRetrievalDate) {
		this.mealRetrievalDate = mealRetrievalDate;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Integer getThepla() {
		return thepla;
	}

	public void setThepla(Integer thepla) {
		this.thepla = thepla;
	}

	public Integer getPuri() {
		return puri;
	}

	public void setPuri(Integer puri) {
		this.puri = puri;
	}

	public Integer getRoti() {
		return roti;
	}

	public void setRoti(Integer roti) {
		this.roti = roti;
	}

	public Integer getAchar() {
		return achar;
	}

	public void setAchar(Integer achar) {
		this.achar = achar;
	}

	public Integer getJam() {
		return jam;
	}

	public void setJam(Integer jam) {
		this.jam = jam;
	}

	public Integer getBread() {
		return bread;
	}

	public void setBread(Integer bread) {
		this.bread = bread;
	}

	public Integer getOthers() {
		return others;
	}

	public void setOthers(Integer others) {
		this.others = others;
	}
	
	
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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