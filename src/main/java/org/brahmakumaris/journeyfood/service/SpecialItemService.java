package org.brahmakumaris.journeyfood.service;

import java.util.List;

import org.brahmakumaris.journeyfood.entity.SpecialItem;

public interface SpecialItemService {

	SpecialItem addItem(SpecialItem item);
	
	List<SpecialItem>  getItems();
	
	SpecialItem  getItemByItem(String item);

	void  removeItem(SpecialItem item);
	
	void update(SpecialItem item);

	SpecialItem getItem(int id);

	void delete(int id);
}