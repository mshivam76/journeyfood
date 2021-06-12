package org.brahmakumaris.journeyfood.service;

import java.util.List;

import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.JourneyFoodOrderCreationParameters;

public interface JourneyFoodService {

	JourneyFoodOrder createJourneyFoodOrder(JourneyFoodOrderCreationParameters parameters);

	List<JourneyFoodOrder> getOrders();
	
}
