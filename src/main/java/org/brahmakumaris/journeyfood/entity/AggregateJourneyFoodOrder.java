package org.brahmakumaris.journeyfood.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class AggregateJourneyFoodOrder {
	/*
	 * SELECT SUM(head_count) as total_head_count,SUM(bread) as total_bread,
	 * SUM(achar) as total_achar,SUM(jam) as total_jam ,SUM(others) as
	 * total_others,SUM(puri) as total_puri,SUM(roti) as total_roti, SUM(thepla) as
	 * total_thepla, CAST(meal_retrieval_time as DATE) FROM journey_food_order WHERE
	 * is_removed=false AND meal_retrieval_time LIKE '2021-09-06%';
	 */
	private long totalHeadCount;
	private long totalBread;
	private long totalAchar;
	private long totalJam;
	private long totalPuri;
	private long totalRoti;
	private long totalThepla;
	@Id
	private LocalDate mealRetrievalDate;
	
	public long getTotalHeadCount() {
		return totalHeadCount;
	}
	public void setTotalHeadCount(long totalHeadCount) {
		this.totalHeadCount = totalHeadCount;
	}
	public long getTotalBread() {
		return totalBread;
	}
	public void setTotalBread(long totalBread) {
		this.totalBread = totalBread;
	}
	public long getTotalAchar() {
		return totalAchar;
	}
	public void setTotalAchar(long totalAchar) {
		this.totalAchar = totalAchar;
	}
	public long getTotalJam() {
		return totalJam;
	}
	public void setTotalJam(long totalJam) {
		this.totalJam = totalJam;
	}
	public long getTotalPuri() {
		return totalPuri;
	}
	public void setTotalPuri(long totalPuri) {
		this.totalPuri = totalPuri;
	}
	public long getTotalRoti() {
		return totalRoti;
	}
	public void setTotalRoti(long totalRoti) {
		this.totalRoti = totalRoti;
	}
	public long getTotalThepla() {
		return totalThepla;
	}
	public void setTotalThepla(long totalThepla) {
		this.totalThepla = totalThepla;
	}
	public LocalDate getMealRetrievalDate() {
		return mealRetrievalDate;
	}
	public void setMealRetrievalDate(LocalDate mealRetrievalDate) {
		this.mealRetrievalDate = mealRetrievalDate;
	}

	
	
	public AggregateJourneyFoodOrder(long totalHeadCount, long totalBread, long totalAchar, long totalJam, 
			long totalPuri, long totalRoti, long totalThepla, LocalDate mealRetrievalDate) {
		super();
		this.totalHeadCount = totalHeadCount;
		this.totalBread = totalBread;
		this.totalAchar = totalAchar;
		this.totalJam = totalJam;
		this.totalPuri = totalPuri;
		this.totalRoti = totalRoti;
		this.totalThepla = totalThepla;
		this.mealRetrievalDate = mealRetrievalDate;
	}
	
	public AggregateJourneyFoodOrder() {
		super();
	}
	
	@Override
	public String toString() {
		return "AggregateJourneyFoodOrder [totalHeadCount=" + totalHeadCount + ", totalBread=" + totalBread
				+ ", totalAchar=" + totalAchar + ", totalJam=" + totalJam + ", totalPuri=" + totalPuri + 
				", totalRoti=" + totalRoti + ", totalThepla=" + totalThepla	+ ", mealRetrievalDate=" + mealRetrievalDate + "]";
	}
}
