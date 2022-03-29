package org.brahmakumaris.journeyfood.service;

import java.time.LocalDate;
import java.util.List;

import org.brahmakumaris.journeyfood.entity.SpecialItemForADate;
import org.brahmakumaris.journeyfood.order.web.AddSpecialItems;

public interface SpecialItemForADateService {

	SpecialItemForADate addItem(AddSpecialItems item);
	
	List<SpecialItemForADate>  getItems();
	
	SpecialItemForADate  getItemsByServingDate(LocalDate date);
	
	List<SpecialItemForADate>  getItemsByDateOfOrder(LocalDate date);
	
	List<SpecialItemForADate>  getItemsByServingDateAndServingSlot(LocalDate date, String slot);
	
	List<SpecialItemForADate>  getItemsByServingSlot(String slot);
	
	void  removeItem(AddSpecialItems item);
	
	void update(AddSpecialItems item);

	SpecialItemForADate getItem(long id);

	void delete(long id);
}