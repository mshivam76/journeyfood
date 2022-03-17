package org.brahmakumaris.journeyfood.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.brahmakumaris.journeyfood.entity.OrderForBreadDepartment;
import org.brahmakumaris.journeyfood.order.web.CreateOrderForBreadDepartment;

public interface OrderBreadDeptService {
	OrderForBreadDepartment createOrder(CreateOrderForBreadDepartment createOrderForKitchenDepartment);
	OrderForBreadDepartment getOrder(LocalDate orderForDate);
	OrderForBreadDepartment getOrder(Date orderDate);
	List<OrderForBreadDepartment> getAllOrder();
	OrderForBreadDepartment updateOrder(CreateOrderForBreadDepartment createOrderForKitchenDepartment);
	OrderForBreadDepartment getOrder(Long id);
}