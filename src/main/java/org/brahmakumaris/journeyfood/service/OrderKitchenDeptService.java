package org.brahmakumaris.journeyfood.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.brahmakumaris.journeyfood.entity.OrderForKitchenDepartment;
import org.brahmakumaris.journeyfood.order.web.CreateOrderForKitchenDepartment;

public interface OrderKitchenDeptService {
	OrderForKitchenDepartment createOrder(CreateOrderForKitchenDepartment createOrderForKitchenDepartment);
	OrderForKitchenDepartment getOrder(LocalDate orderForDate);
	List<OrderForKitchenDepartment> getAllOrder();
	OrderForKitchenDepartment getOrder(Date orderDate);
	OrderForKitchenDepartment getOrder(Long id);
	OrderForKitchenDepartment updateOrder(CreateOrderForKitchenDepartment createOrderForKitchenDepartment);
}
