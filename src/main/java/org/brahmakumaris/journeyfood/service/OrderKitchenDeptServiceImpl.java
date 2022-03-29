package org.brahmakumaris.journeyfood.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.brahmakumaris.journeyfood.entity.OrderForKitchenDepartment;
import org.brahmakumaris.journeyfood.order.web.CreateOrderForKitchenDepartment;
import org.brahmakumaris.journeyfood.repository.OrderKitchenDeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderKitchenDeptServiceImpl implements OrderKitchenDeptService {
	@Autowired
	OrderKitchenDeptRepository repository;
	
	@Override
	public OrderForKitchenDepartment createOrder(CreateOrderForKitchenDepartment createOrderForKitchenDepartment) {
		OrderForKitchenDepartment order = new OrderForKitchenDepartment();
		order.setChips(createOrderForKitchenDepartment.getChips());
		order.setChutney(createOrderForKitchenDepartment.getChutney());
		order.setCurdRice(createOrderForKitchenDepartment.getCurdRice());
		order.setIdli(createOrderForKitchenDepartment.getIdli());
		order.setOrderDate(createOrderForKitchenDepartment.getOrderDate());
		order.setOrderForDate(createOrderForKitchenDepartment.getOrderForDate());
		order.setSlot(createOrderForKitchenDepartment.getSlot());
		order.setSoojiDhokla(createOrderForKitchenDepartment.getSoojiDhokla());
		order.setTomatoRice(createOrderForKitchenDepartment.getTomatoRice());
		return repository.save(order);
	}
	
	@Override
	public OrderForKitchenDepartment updateOrder(CreateOrderForKitchenDepartment createOrderForKitchenDepartment) {
		OrderForKitchenDepartment order = repository.getOne(createOrderForKitchenDepartment.getKitchenOrderId());
		order.setChips(createOrderForKitchenDepartment.getChips());
		order.setChutney(createOrderForKitchenDepartment.getChutney());
		order.setCurdRice(createOrderForKitchenDepartment.getCurdRice());
		order.setIdli(createOrderForKitchenDepartment.getIdli());
		order.setOrderDate(createOrderForKitchenDepartment.getOrderDate());
		order.setOrderForDate(createOrderForKitchenDepartment.getOrderForDate());
		order.setSlot(createOrderForKitchenDepartment.getSlot());
		order.setSoojiDhokla(createOrderForKitchenDepartment.getSoojiDhokla());
		order.setTomatoRice(createOrderForKitchenDepartment.getTomatoRice());
		return repository.save(order);
	}

	@Override
	public OrderForKitchenDepartment getOrder(LocalDate orderForDate) {
		return repository.findByOrderForDate(orderForDate);
	}
	
	@Override
	public OrderForKitchenDepartment getOrder(Long id) {
		return repository.findByKitchenDeptId(id);
	}
	
	@Override
	public OrderForKitchenDepartment getOrder(Date orderDate) {
		return repository.findByOrderDate(orderDate);
	}
	
	@Override
	public List<OrderForKitchenDepartment> getAllOrder() {
		return repository.findAll();
	}
}