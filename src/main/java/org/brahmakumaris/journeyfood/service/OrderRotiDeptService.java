package org.brahmakumaris.journeyfood.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.brahmakumaris.journeyfood.entity.OrderForRotiDepartment;
import org.brahmakumaris.journeyfood.order.web.CreateOrderForRotiDepartment;

public interface OrderRotiDeptService {
	OrderForRotiDepartment createOrder(CreateOrderForRotiDepartment createOrderForRotiDepartment);
	OrderForRotiDepartment getOrder(LocalDate orderForDate);
	List<OrderForRotiDepartment> getAllOrder();
	OrderForRotiDepartment getOrder(Date orderDate);
	OrderForRotiDepartment getOrder(Long id);
	OrderForRotiDepartment updateOrder(CreateOrderForRotiDepartment createOrderForRotiDepartment);
}