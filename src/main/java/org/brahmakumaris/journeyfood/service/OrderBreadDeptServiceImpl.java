package org.brahmakumaris.journeyfood.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.brahmakumaris.journeyfood.entity.OrderForBreadDepartment;
import org.brahmakumaris.journeyfood.order.web.CreateOrderForBreadDepartment;
import org.brahmakumaris.journeyfood.repository.OrderBreadDeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderBreadDeptServiceImpl implements OrderBreadDeptService {
	
	@Autowired
	private OrderBreadDeptRepository repository;
	
	@Override
	public OrderForBreadDepartment createOrder(CreateOrderForBreadDepartment createOrderForKitchenDepartment) {
		OrderForBreadDepartment order = new OrderForBreadDepartment();
		order.setBread(createOrderForKitchenDepartment.getBread());
		order.setSlicedBread(createOrderForKitchenDepartment.getSlicedBread());
		order.setOrderDate(createOrderForKitchenDepartment.getOrderDate());
		order.setOrderForDate(createOrderForKitchenDepartment.getOrderForDate());
		order.setSlot(createOrderForKitchenDepartment.getSlot());
		return repository.save(order);
	}
	
	@Override
	public OrderForBreadDepartment updateOrder(CreateOrderForBreadDepartment createOrderForKitchenDepartment) {
		OrderForBreadDepartment order = repository.getOne(createOrderForKitchenDepartment.getBreadOrderId());
		order.setBread(createOrderForKitchenDepartment.getBread());
		order.setSlicedBread(createOrderForKitchenDepartment.getSlicedBread());
		order.setOrderDate(createOrderForKitchenDepartment.getOrderDate());
		order.setOrderForDate(createOrderForKitchenDepartment.getOrderForDate());
		order.setSlot(createOrderForKitchenDepartment.getSlot());
		return repository.save(order);
	}

	@Override
	public OrderForBreadDepartment getOrder(LocalDate orderForDate) {
		return repository.findByOrderForDate(orderForDate);
	}
	
	@Override
	public List<OrderForBreadDepartment> getAllOrder() {
		return repository.findAll();
	}

	@Override
	public OrderForBreadDepartment getOrder(Date orderDate) {
		return repository.findByOrderDate(orderDate);
	}

	@Override
	public OrderForBreadDepartment getOrder(Long id) {
		return repository.getOne(id);
	}
}