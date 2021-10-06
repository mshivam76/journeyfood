package org.brahmakumaris.journeyfood.order.web;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.brahmakumaris.journeyfood.validation.IntegerCountMustBeGreaterThanZeroConstraint;

public class UpdateJourneyFoodOrderFormData {
	private long orderId;
	
	@IntegerCountMustBeGreaterThanZeroConstraint(message="Head count must be greater than 0")
    private int headCount;
    
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
    
	@NotBlank(message="Other items count is mandatory")
    private String others;
    
    private String orderStatus;
    
	public UpdateJourneyFoodOrderFormData() {
		super();
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getHeadCount() {
		return headCount;
	}

	public void setHeadCount(int headCount) {
		this.headCount = headCount;
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

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

}
