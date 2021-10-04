package org.brahmakumaris.journeyfood.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.brahmakumaris.journeyfood.entity.AggregateJourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.CreateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.order.web.JourneyFoodOrderCreationParameters;
import org.brahmakumaris.journeyfood.order.web.UpdateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.security.exceptions.OrderNotFoundException;

public interface JourneyFoodService {

	JourneyFoodOrder createJourneyFoodOrder(JourneyFoodOrderCreationParameters parameters) throws UnsupportedEncodingException, MessagingException;

	List<JourneyFoodOrder> getOrders();

	UserEntity getCurrentLoggedInUserData();

	JourneyFoodOrder findByOrderId(long id);

	List<JourneyFoodOrder> getOrdersByUser();

	void delete(long id) throws UnsupportedEncodingException, MessagingException;

	void update(long id);

	void updateOrder(UpdateJourneyFoodOrderFormData order);

	List<JourneyFoodOrder> getOrdersNotDisabledData();

	void orderCompleted(long id) throws UnsupportedEncodingException, MessagingException ;

	AggregateJourneyFoodOrder getOrdersByDateAndNotDisabled(LocalDate mealRetrievalDate);

	void updateOrderAdmin(CreateJourneyFoodOrderFormData order) throws UnsupportedEncodingException, MessagingException;

	List<JourneyFoodOrder> getOrdersByDate(LocalDate mealRetrievalDate);
	
	List<JourneyFoodOrder> getOrdersByDate(LocalDate mealRetrievalDate, String orderStatus);

	List<JourneyFoodOrder> getOrdersByDateRange(LocalDate fromDate, LocalDate endDate) ;

	List<JourneyFoodOrder> getOrdersByDateRangeAndOrderStatus(LocalDate fromDate, LocalDate endDate,
			String orderStatus);
}
