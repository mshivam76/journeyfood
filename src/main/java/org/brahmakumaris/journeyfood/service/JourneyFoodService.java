package org.brahmakumaris.journeyfood.service;

import java.util.List;
import java.util.Optional;

import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.CreateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.order.web.JourneyFoodOrderCreationParameters;

public interface JourneyFoodService {

	JourneyFoodOrder createJourneyFoodOrder(JourneyFoodOrderCreationParameters parameters);

	List<JourneyFoodOrder> getOrders();

	UserEntity getCurrentLoggedInUserData();

	JourneyFoodOrder findByOrderId(long id);

	List<JourneyFoodOrder> getOrdersByUser(UserEntity user);

	void delete(long id);

	void update(long id);

	void updateOrder(CreateJourneyFoodOrderFormData order);
}
