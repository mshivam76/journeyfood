package org.brahmakumaris.journeyfood.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.brahmakumaris.journeyfood.entity.OrderForRotiDepartment;
import org.brahmakumaris.journeyfood.order.web.CreateOrderForRotiDepartment;
import org.brahmakumaris.journeyfood.repository.OrderRotiDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderRotiDeptServiceImpl implements OrderRotiDeptService {

	@Autowired
	private OrderRotiDepartmentRepository repository;
	
	@Override
	public OrderForRotiDepartment createOrder(CreateOrderForRotiDepartment createOrderForRotiDepartment) {
		OrderForRotiDepartment orderForRotiDepartment = new OrderForRotiDepartment();
		orderForRotiDepartment.setOrderDate(createOrderForRotiDepartment.getOrderDate());
		orderForRotiDepartment.setOrderForDate(createOrderForRotiDepartment.getOrderForDate());
		orderForRotiDepartment.setPuri(createOrderForRotiDepartment.getPuri());
		orderForRotiDepartment.setRoti(createOrderForRotiDepartment.getRoti());
		orderForRotiDepartment.setSandwich(createOrderForRotiDepartment.getSandwich());
		orderForRotiDepartment.setSlot(createOrderForRotiDepartment.getSlot());
		orderForRotiDepartment.setThepla(createOrderForRotiDepartment.getThepla());
		return repository.save(orderForRotiDepartment);
	}
	
	@Override
	public OrderForRotiDepartment updateOrder(CreateOrderForRotiDepartment createOrderForRotiDepartment) {
		OrderForRotiDepartment orderForRotiDepartment = repository.getOne(createOrderForRotiDepartment.getRotiDeptId());
		orderForRotiDepartment.setOrderDate(createOrderForRotiDepartment.getOrderDate());
		orderForRotiDepartment.setOrderForDate(createOrderForRotiDepartment.getOrderForDate());
		orderForRotiDepartment.setPuri(createOrderForRotiDepartment.getPuri());
		orderForRotiDepartment.setRoti(createOrderForRotiDepartment.getRoti());
		orderForRotiDepartment.setSandwich(createOrderForRotiDepartment.getSandwich());
		orderForRotiDepartment.setSlot(createOrderForRotiDepartment.getSlot());
		orderForRotiDepartment.setThepla(createOrderForRotiDepartment.getThepla());
		return repository.save(orderForRotiDepartment);
	}

	@Override
	public OrderForRotiDepartment getOrder(LocalDate orderForDate) {
		return repository.findByOrderForDate(orderForDate);
	}

	@Override
	public List<OrderForRotiDepartment> getAllOrder() {
		return repository.findAll();
	}

	@Override
	public OrderForRotiDepartment getOrder(Date orderDate) {
		return repository.findByOrderDate(orderDate);
	}

	@Override
	public OrderForRotiDepartment getOrder(Long id) {
		return repository.getOne(id);
	}
}