package org.brahmakumaris.journeyfood.excel;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

import org.brahmakumaris.journeyfood.entity.AggregateJourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.security.UserService;
import org.brahmakumaris.journeyfood.service.JourneyFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcelService {
	@Autowired
	private JourneyFoodService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ExcelUtils excelUtils;

	public ByteArrayInputStream loadUsers() {
		List<UserEntity> users = userService.getUsers();
		ByteArrayInputStream in = ExcelUtils.usersToExcel(users);
		return in;
	}
	
	public ByteArrayInputStream loadOrders() {
		List<JourneyFoodOrder> orders = orderService.getOrders();
		ByteArrayInputStream in = excelUtils.ordersToExcel(orders);
		return in;
	}
	
	public ByteArrayInputStream loadOrdersForADate(LocalDate date) {
		List<JourneyFoodOrder> orders = orderService.getOrders();
		ByteArrayInputStream in = excelUtils.ordersToExcel(orders);
		return in;
	}
	
	public ByteArrayInputStream loadPlacedOrdersForADate(LocalDate date) {
		List<JourneyFoodOrder> orders = orderService.getOrdersByDate(date, "PLACED");
		ByteArrayInputStream in = excelUtils.ordersToExcel(orders);
		return in;
	}
	
	public ByteArrayInputStream loadCancelledOrdersForADate(LocalDate date) {
		List<JourneyFoodOrder> orders = orderService.getOrdersByDate(date, "CANCELLED");
		ByteArrayInputStream in = excelUtils.ordersToExcel(orders);
		return in;
	}
	
	public ByteArrayInputStream loadDeliveredOrdersForADate(LocalDate date) {
		List<JourneyFoodOrder> orders = orderService.getOrdersByDate(date, "DELIVERED");
		ByteArrayInputStream in = excelUtils.ordersToExcel(orders);
		return in;
	}
	
	public ByteArrayInputStream loadDeliveredOrdersFromDate2EndDateWithOrderStatus(LocalDate start, LocalDate end, String orderStatus) {
		List<JourneyFoodOrder> orders = orderService.getOrdersByDateRangeAndOrderStatus(start, end, orderStatus);
		ByteArrayInputStream in = excelUtils.ordersToExcel(orders);
		return in;
	}
	
	public ByteArrayInputStream loadDeliveredOrdersFromDate2EndDate(LocalDate start, LocalDate end) {
		List<JourneyFoodOrder> orders = orderService.getOrdersByDateRange(start, end);
		ByteArrayInputStream in = excelUtils.ordersToExcel(orders);
		return in;
	}	

	public ByteArrayInputStream loadTotalQuantityOrdersForDate(LocalDate date) {
		List<JourneyFoodOrder> listOrders = orderService.getOrdersByDate(date, "PLACED");
		AggregateJourneyFoodOrder totalOrder=orderService.getOrdersByDateAndNotDisabled(date);
		ByteArrayInputStream in = excelUtils.totalOrdersToExcel(listOrders, totalOrder);
		return in;
	}
}
