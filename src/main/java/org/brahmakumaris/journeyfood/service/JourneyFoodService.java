package org.brahmakumaris.journeyfood.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.brahmakumaris.journeyfood.entity.AggregateJourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.CreateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.order.web.JourneyFoodOrderCreationParameters;
import org.brahmakumaris.journeyfood.order.web.UpdateJourneyFoodOrderFormData;

public interface JourneyFoodService {

	JourneyFoodOrder createJourneyFoodOrder(JourneyFoodOrderCreationParameters parameters);

	List<JourneyFoodOrder> getOrders();

	UserEntity getCurrentLoggedInUserData();

	JourneyFoodOrder findByOrderId(long id);

	List<JourneyFoodOrder> getOrdersByUser();

	void delete(long id);

	void update(long id);

	void updateOrder(UpdateJourneyFoodOrderFormData order);

	List<JourneyFoodOrder> getOrdersNotDisabledData();

	void orderCompleted(long id) throws IllegalArgumentException;

	AggregateJourneyFoodOrder getOrdersByDateAndNotDisabled(LocalDate mealRetrievalDate);

	void updateOrderAdmin(CreateJourneyFoodOrderFormData order);

	List<JourneyFoodOrder> getOrdersByDate(LocalDate mealRetrievalDate);
	
	List<JourneyFoodOrder> getOrdersByDate(LocalDate mealRetrievalDate, String orderStatus);

	List<JourneyFoodOrder> getOrdersByDateRange(LocalDate fromDate, LocalDate endDate);

	List<JourneyFoodOrder> getOrdersByDateRangeAndOrderStatus(LocalDate fromDate, LocalDate endDate,
			String orderStatus);
}
