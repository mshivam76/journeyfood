package org.brahmakumaris.journeyfood.order.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.brahmakumaris.journeyfood.validation.IntegerCountMustBeGreaterThanZeroConstraint;
import org.springframework.format.annotation.DateTimeFormat;

public class CreateJourneyFoodOrderFormData {
	private long id;
	
	@IntegerCountMustBeGreaterThanZeroConstraint(message="Head count must be greater than 0")
    private int headCount;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfOrderPlaced;
    
    @NotNull(message="Departure date is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Future(message = "Entered date must be after today\'s date")
    private Date dateOfDeparture;
    
	@NotNull(message="Meal retrieval time is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy h:mm a")
	@Future(message = "Entered date must be after today\'s date")
    private Date mealRetrievalTime;

    @NotNull(message="Thepla Count is mandatory")
    private int thepla;
    
    @NotNull(message="Puri Count is mandatory")
    private int puri;
    
    @NotNull(message="Roti Count is mandatory")
    private int roti;

    @NotNull(message="Achar count is mandatory")
    private int achar;
    
    @NotNull(message="Jam count is mandatory")
    private int jam;
    
	@NotNull(message="Bread count is mandatory")
    private int bread;
    
    @NotNull(message="Other items count is mandatory")
    private int others;
    
	public JourneyFoodOrderCreationParameters toParams() {
		return new JourneyFoodOrderCreationParameters(headCount, dateOfOrderPlaced, dateOfDeparture, mealRetrievalTime, null, thepla, puri, roti, achar, jam, bread, others);
    }
    
	
	public CreateJourneyFoodOrderFormData() {
		super();
//		String pattern = "EEEEE dd MM yyyy HH:mm:ss.SSSZ";
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("en", "IN"));
//			try {
//				this.dateOfOrderPlaced = simpleDateFormat.parse( simpleDateFormat.format(new Date()));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		this.dateOfOrderPlaced = new Date();
	}

	 public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDateOfOrderPlaced() {
		return dateOfOrderPlaced;
	}

	public void setDateOfOrderPlaced(Date dateOfOrderPlaced) {
		this.dateOfOrderPlaced = dateOfOrderPlaced;
	}

	public int getHeadCount() {
		return headCount;
	}

	public void setHeadCount(int headCount) {
		this.headCount = headCount;
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
}
