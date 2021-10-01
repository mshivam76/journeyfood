package org.brahmakumaris.journeyfood.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.brahmakumaris.journeyfood.entity.AggregateJourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.CreateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.order.web.SubmitFetchOrdersFromDate2EndDate;
import org.brahmakumaris.journeyfood.order.web.SubmitFetchOrdersFromDate2EndDateOrderStatus;
import org.brahmakumaris.journeyfood.order.web.SubmitFetchTotalQuantityModelByDate;
import org.brahmakumaris.journeyfood.order.web.UserUpdateForm;
import org.brahmakumaris.journeyfood.security.UserService;
import org.brahmakumaris.journeyfood.service.JourneyFoodService;
import org.brahmakumaris.journeyfood.utils.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private JourneyFoodService journeyFoodServiceImpl;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ExcelUtils excelUtils;
	
	@GetMapping("/error")
    public String error() {
        return "error";
    }
	
	@GetMapping("/fetchAllJourneyFoodOrder")
    public ModelAndView fetchAllJourneyFoodOrder() {
		LOGGER.info("AdminController fetchAllJourneyFoodOrder method - Enter");
	 	List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrders();
	 	LOGGER.info("AdminController fetchAllJourneyFoodOrder method - Exit =>orders: "+orders);
        return new ModelAndView("fethAllJourneyOrdersByAdmin", "orders", orders.isEmpty()?null:orders);
    }
	
	@GetMapping("/fetchAllJourneyFoodOrdersNotDisabled")
    public ModelAndView fetchAllJourneyFoodOrdersNotDisabled() {
		LOGGER.info("AdminController fetchAllJourneyFoodOrder method - Enter");
	 	List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrdersNotDisabledData();
	 	LOGGER.info("AdminController fetchAllJourneyFoodOrder method - Exit =>orders: "+orders);
        return new ModelAndView("fethAllJourneyOrdersByAdmin", "orders", orders.isEmpty()?null:orders);
    }
	
	@GetMapping("/fetchAllPlacedOrdersForADate")
	public String fetchAllPlacedOrdersForADate(SubmitFetchTotalQuantityModelByDate submitFetchTotalQuantityModelByDate) {
		return "getAllPlacedOrdersForADate";
	}
	
	@PostMapping("/fetchAllPlacedOrdersForADate")
    public String fetchAllPlacedOrdersForADate( @Valid @ModelAttribute("submitFetchTotalQuantityModelByDate") SubmitFetchTotalQuantityModelByDate submitFetchTotalQuantityModelByDate
    		, BindingResult result, Model model) {
		if (result.hasErrors()) {
	    	LOGGER.error("AdminController updateOrder method - Error occured");
            return "getAllPlacedOrdersForADate";
	    }
		LOGGER.info("AdminController fetchSumOfJourneyFoodOrdersNotDisabled method - Enter");
		List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrdersByDate(submitFetchTotalQuantityModelByDate.getMealRetrievalDate(), "PLACED");
	 	 model.addAttribute("orders", orders.isEmpty()?null:orders);
	 	LOGGER.info("AdminController fetchSumOfJourneyFoodOrdersNotDisabled method - Exit =>orders: "+orders);
        return "showOrdersByDate";
    }
	
	@GetMapping("/users/export/excel")
    public void exportUsersToExcel(HttpServletResponse response) throws IOException {
		LOGGER.info("AdminController exportUsersToExcel method - Enter ");
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        List<UserEntity> listUsers = userService.getUsers();
        try {
        	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            ExcelUtils excelExporter = new ExcelUtils();
            excelExporter.setUsers(listUsers);
            excelExporter.exportUsers(response);
            LOGGER.info("AdminController exportUsersToExcel method - Exist- successfully ");
			/*
			 * String filename = "tutorials.xlsx"; InputStreamResource file = new
			 * InputStreamResource(fileService.load());
			 * 
			 * return ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
			 * "attachment; filename=" + filename)User 
			 * .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
			 * .body(file);
			 */
        }catch (Exception e) {
        	// TODO: handle exception
        	LOGGER.error("ERROR: AdminController  User exportUsersToExcel method - "+ e.getMessage());
		}
        
	}
	
	@GetMapping("/orders/export/excel/{date}")
    public Object exportOrdersToExcel(HttpServletResponse response) throws IOException {
		LOGGER.info("AdminController exportOrdersToExcel method - Enter ");
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        List<JourneyFoodOrder> listOrders = journeyFoodServiceImpl.getOrders();
        try {
        	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());
            String headerValue = "attachment; filename=orders_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            ExcelUtils excelExporter = new ExcelUtils();
            excelExporter.setOrders(listOrders);
            excelExporter.exportOrders(response);
            LOGGER.info("AdminController exportOrdersToExcel method - Exist- successfully ");
            return "showTotalQuantityForADate";
        }catch (Exception e) {
        	LOGGER.error("ERROR: AdminController  Order exportOrdersToExcel method - "+ e.getMessage());
        	return "getTotalQuantityForADate";
		}
    }   
    
	@GetMapping("/orders/totalPlacedOrders/export/excel/{date}")
    public void exportTotalOrdersForADateToExcel(@PathVariable("date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, HttpServletResponse response) throws IOException {
		LOGGER.info("AdminController exportTotalOrdersForADateToExcel method - Enter ");
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        List<JourneyFoodOrder> listOrders = journeyFoodServiceImpl.getOrdersByDate(date, "PLACED");
        AggregateJourneyFoodOrder totalOrder=journeyFoodServiceImpl.getOrdersByDateAndNotDisabled(date);
        try {
            String headerValue = "attachment; filename=totalOrders_" + date.toString() + ".xlsx";
            response.setHeader(headerKey, headerValue);
            ExcelUtils excelExporter = new ExcelUtils();
            excelExporter.setOrders(listOrders);
            excelExporter.setTotalQuantity(totalOrder);
            excelExporter.exportTotalOrders(response);
            LOGGER.info("AdminController exportTotalOrdersForADateToExcel method - Exist- successfully ");
        }catch (Exception e) {
        	LOGGER.error("ERROR: AdminController  Order exportTotalOrdersForADateToExcel method - "+ e.getMessage());
		}
    }
	
	@GetMapping("/orders/canceledOrders/export/excel/{date}")
    public void exportCanceledOrdersForADateToExcel(@PathVariable("date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, HttpServletResponse response) throws IOException {
		LOGGER.info("AdminController exportCanceledOrdersForADateToExcel method - Enter ");
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        List<JourneyFoodOrder> listOrders = journeyFoodServiceImpl.getOrdersByDate(date, "CANCELLED");
        try {
            String headerValue = "attachment; filename=CanceledOrders_" + date.toString() + ".xlsx";
            response.setHeader(headerKey, headerValue);
            ExcelUtils excelExporter = new ExcelUtils();
            excelExporter.setOrders(listOrders);
            excelExporter.exportOrders(response);
            LOGGER.info("AdminController exportCanceledOrdersForADateToExcel method - Exist- successfully ");
        }catch (Exception e) {
        	LOGGER.error("ERROR: AdminController  Order exportCanceledOrdersForADateToExcel method - "+ e.getMessage());
		}
    } 
	
	@GetMapping("/orders/dateRangeOrdersWithOrderStatus/export/excel")
    public void exportFromDate2EndDateOrdersWithOrderStatusToExcel(
    		@RequestParam(name = "startDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, 
    		@RequestParam(name = "endDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
    		@RequestParam(name = "orderStatus") String orderStatus,
    		HttpServletResponse response) throws IOException {
		LOGGER.info("AdminController exportFromDate2EndDateOrdersToExcel method - Enter ");
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        List<JourneyFoodOrder> listOrders = journeyFoodServiceImpl.getOrdersByDateRangeAndOrderStatus(startDate,endDate,orderStatus);
        try {
        	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        	String currentDateTime = dateFormatter.format(new Date());
            String headerValue = "attachment; filename=AllOrders_" + currentDateTime.toString() + ".xlsx";
            response.setHeader(headerKey, headerValue);
            ExcelUtils excelExporter = new ExcelUtils();
            excelExporter.setOrders(listOrders);
            excelExporter.exportOrders(response);
            LOGGER.info("AdminController exportFromDate2EndDateOrdersToExcel method - Exist- successfully ");
        }catch (Exception e) {
        	LOGGER.error("ERROR: AdminController  Order exportFromDate2EndDateOrdersToExcel method - "+ e.getMessage());
		}
    }
	
	@GetMapping("/orders/dateRangeOrders/export/excel")
    public void exportFromDate2EndDateOrdersToExcel(
    		@RequestParam(name = "startDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, 
    		@RequestParam(name = "endDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
    		HttpServletResponse response) throws IOException {
		LOGGER.info("AdminController exportFromDate2EndDateOrdersToExcel method - Enter ");
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        List<JourneyFoodOrder> listOrders = journeyFoodServiceImpl.getOrdersByDateRange(startDate,endDate);
        try {
        	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        	String currentDateTime = dateFormatter.format(new Date());
            String headerValue = "attachment; filename=AllOrders_" + currentDateTime.toString() + ".xlsx";
            response.setHeader(headerKey, headerValue);
            ExcelUtils excelExporter = new ExcelUtils();
            excelExporter.setOrders(listOrders);
            excelExporter.exportOrders(response);
            LOGGER.info("AdminController exportFromDate2EndDateOrdersToExcel method - Exist- successfully ");
        }catch (Exception e) {
        	LOGGER.error("ERROR: AdminController  Order exportFromDate2EndDateOrdersToExcel method - "+ e.getMessage());
		}
    } 
	
	@GetMapping("/fetchFromDate2EndDateWithOrderStatusOrders")
	public String fetchFromDate2EndDateWithOrderStatusOrders(SubmitFetchOrdersFromDate2EndDateOrderStatus submitFetchOrdersFromDate2EndDateStatus) {
		return "getOrderStartDate2EndDateWithOrderStatus";
	}
	
	@PostMapping("/fetchFromDate2EndDateWithOrderStatusOrders")
    public String fetchFromDate2EndDateWithOrderStatusOrders( 
    		@Valid  @ModelAttribute("submitFetchOrdersFromDate2EndDateStatus")SubmitFetchOrdersFromDate2EndDateOrderStatus submitFetchOrdersFromDate2EndDateOrderStatus,
    		BindingResult result, Model model) {
		if (result.hasErrors()) {
	    	LOGGER.error("AdminController updateOrder method - Error occured");
            return "getOrderStartDate2EndDateWithOrderStatus";
	    }
		LOGGER.info("AdminController fetchSumOfJourneyFoodOrdersNotDisabled method - Enter");
		List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrdersByDateRangeAndOrderStatus(submitFetchOrdersFromDate2EndDateOrderStatus.getStartDate(),submitFetchOrdersFromDate2EndDateOrderStatus.getEndDate(),submitFetchOrdersFromDate2EndDateOrderStatus.getOrderStatus());
	 	model.addAttribute("orders", orders.isEmpty()?null:orders);
	 	model.addAttribute("startDate", submitFetchOrdersFromDate2EndDateOrderStatus==null?null:submitFetchOrdersFromDate2EndDateOrderStatus.getStartDate());
	 	model.addAttribute("endDate", submitFetchOrdersFromDate2EndDateOrderStatus==null?null:submitFetchOrdersFromDate2EndDateOrderStatus.getEndDate());
	 	model.addAttribute("orderStatus", submitFetchOrdersFromDate2EndDateOrderStatus==null?null:submitFetchOrdersFromDate2EndDateOrderStatus.getOrderStatus());
	 	LOGGER.info("AdminController fetchSumOfJourneyFoodOrdersNotDisabled method - Exit =>orders: "+orders);
        return "showOrdersStartDate2EndDateWithStatus";
    }
	
	@GetMapping("/fetchFromDate2EndDateOrders")
	public String fetchFromDate2EndDateOrders(SubmitFetchOrdersFromDate2EndDate submitFetchOrdersFromDate2EndDate) {
		return "getOrdersStartDate2EndDate";
	}
	
	@PostMapping("/fetchFromDate2EndDateOrders")
    public String fetchFromDate2EndDateOrders( @Valid SubmitFetchOrdersFromDate2EndDate submitFetchOrdersFromDate2EndDate
    		, BindingResult result, Model model) {
		if (result.hasErrors()) {
	    	LOGGER.error("AdminController updateOrder method - Error occured");
            return "getOrdersStartDate2EndDate";
	    }
		LOGGER.info("AdminController fetchSumOfJourneyFoodOrdersNotDisabled method - Enter");
		List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrdersByDateRange(submitFetchOrdersFromDate2EndDate.getStartDate(),submitFetchOrdersFromDate2EndDate.getEndDate());
	 	model.addAttribute("orders", orders.isEmpty()?null:orders);
	 	model.addAttribute("startDate", submitFetchOrdersFromDate2EndDate==null?null:submitFetchOrdersFromDate2EndDate.getStartDate());
	 	model.addAttribute("endDate", submitFetchOrdersFromDate2EndDate==null?null:submitFetchOrdersFromDate2EndDate.getEndDate());
	 	LOGGER.info("AdminController fetchSumOfJourneyFoodOrdersNotDisabled method - Exit =>orders: "+orders);
        return "showOrdersStartDate2EndDate";
    }
	
	@GetMapping("/fetchAllCanceledOrdersForADate")
	public String fetchAllCanceledOrdersForADate(SubmitFetchTotalQuantityModelByDate submitFetchTotalQuantityModelByDate) {
		return "getAllCancelledOrdersForADate";
	}
	
	@PostMapping("/fetchAllCanceledOrdersForADate")
    public String fetchAllCanceledOrdersForADate( @Valid @ModelAttribute("submitFetchTotalQuantityModelByDate") SubmitFetchTotalQuantityModelByDate submitFetchTotalQuantityModelByDate
    		, BindingResult result, Model model) {
		if (result.hasErrors()) {
	    	LOGGER.error("AdminController updateOrder method - Error occured");
            return "getAllCancelledOrdersForADate";
	    }
		LOGGER.info("AdminController fetchSumOfJourneyFoodOrdersNotDisabled method - Enter");
		List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrdersByDate(submitFetchTotalQuantityModelByDate.getMealRetrievalDate(),"CANCELLED");
	 	model.addAttribute("orders", orders.isEmpty()?null:orders);
	 	model.addAttribute("date", submitFetchTotalQuantityModelByDate==null?null:submitFetchTotalQuantityModelByDate.getMealRetrievalDate());
	 	LOGGER.info("AdminController fetchSumOfJourneyFoodOrdersNotDisabled method - Exit =>orders: "+orders);
        return "showOrdersByDate";
    }
	
	@GetMapping("/fetchAllOrdersForADate")
	public String fetchAllOrdersForADate(SubmitFetchTotalQuantityModelByDate submitFetchTotalQuantityModelByDate) {
		return "getAllOrdersForADate";
	}
	
	@PostMapping("/fetchAllOrdersForADate")
    public String fetchAllOrdersForADate( @Valid @ModelAttribute("submitFetchTotalQuantityModelByDate") SubmitFetchTotalQuantityModelByDate submitFetchTotalQuantityModelByDate
    		, BindingResult result, Model model) {
		if (result.hasErrors()) {
	    	LOGGER.error("AdminController updateOrder method - Error occured");
            return "getAllOrdersForADate";
	    }
		LOGGER.info("AdminController fetchSumOfJourneyFoodOrdersNotDisabled method - Enter");
		List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrdersByDate(submitFetchTotalQuantityModelByDate.getMealRetrievalDate());
	 	model.addAttribute("orders", orders.isEmpty()?null:orders);
	 	model.addAttribute("date", submitFetchTotalQuantityModelByDate==null?null:submitFetchTotalQuantityModelByDate.getMealRetrievalDate());
	 	LOGGER.info("AdminController fetchSumOfJourneyFoodOrdersNotDisabled method - Exit =>orders: "+orders);
        return "showOrdersByDate";
    }
	
	@GetMapping("/fetchTotalQuantityForADate")
	public String fetchTotalQuantityForADate(SubmitFetchTotalQuantityModelByDate submitFetchTotalQuantityModelByDate) {
		return "getTotalQuantityForADate";
	}
	
	@PostMapping("/fetchTotalQuantityForADate")
    public String fetchSumOfJourneyFoodOrdersNotDisabled( @Valid @ModelAttribute("submitFetchTotalQuantityModelByDate") SubmitFetchTotalQuantityModelByDate submitFetchTotalQuantityModelByDate
    		, BindingResult result, Model model) {
		if (result.hasErrors()) {
	    	LOGGER.error("AdminController updateOrder method - Error occured");
            return "getTotalQuantityForADate";
	    }
		LOGGER.info("AdminController fetchSumOfJourneyFoodOrdersNotDisabled method - Enter");
		AggregateJourneyFoodOrder totalOrder=journeyFoodServiceImpl.getOrdersByDateAndNotDisabled(submitFetchTotalQuantityModelByDate.getMealRetrievalDate());
//		List <JourneyFoodOrder> orders = journeyFoodServiceImpl.getOrdersByDate(submitFetchTotalQuantityModelByDate.getMealRetrievalDate(), "PLACED");
	 	model.addAttribute("order", totalOrder==null?null:totalOrder);
	 	model.addAttribute("date", submitFetchTotalQuantityModelByDate==null?null:submitFetchTotalQuantityModelByDate.getMealRetrievalDate());
	 	LOGGER.info("AdminController fetchSumOfJourneyFoodOrdersNotDisabled method - Exit =>orders: "+totalOrder);
        return "showAggregateQuantityOrdersByDate";
    }
	
	@GetMapping("/fetchAllUsers")
    public ModelAndView fetchAllUsers() {
		LOGGER.info("AdminController fetchAllUsers method - Enter");
	 	List<UserEntity> users=userService.getUsers();
	 	LOGGER.info("AdminController fetchAllUsers method - Exit =>users: "+users);
        return new ModelAndView("fetchAllUsers", "users", users.isEmpty()?null:users);
    }
	
	@GetMapping("/order/delete/{id}")
	public String deleteOrder(@PathVariable("id") long id, Model model) {
		LOGGER.info("AdminController deleteOrder method - Enter =>id :"+id);
		try {
			journeyFoodServiceImpl.delete(id);
		    LOGGER.info("AdminController deleteOrder method - Exit successful");
	    }
		catch(IllegalArgumentException e) {
			LOGGER.error("AdminController deleteOrder method - Exit"+ e.getMessage());
		}
	    return "redirect:/admin/fetchAllJourneyFoodOrder";
	}
	
	@GetMapping("/order/delivered/{id}")
	public String deliveredOrder(@PathVariable("id") long id, Model model) {
		LOGGER.info("AdminController updateOrder method - Enter =>id :"+id);
		JourneyFoodOrder order =null;
	    try {
	    	journeyFoodServiceImpl.orderCompleted(id);
		    LOGGER.info("AdminController updateOrder method - Exit =>order(object/null): "+ order);
	    }
		catch(IllegalArgumentException e) {
			LOGGER.info("AdminController updateOrder method - Exit =>orders: "+order);
			return "redirect:/admin/error";
		}
	    return "redirect:/admin/fetchAllJourneyFoodOrder";
	}
	
	@GetMapping("/order/edit/{id}")
	public String updateOrder(@PathVariable("id") long id, Model model) {
		LOGGER.info("AdminController updateOrder method - Enter =>id :"+id);
		JourneyFoodOrder order =null;
	    try {
	    	order = journeyFoodServiceImpl.findByOrderId(id);
	    	model.addAttribute("order", order);
		    LOGGER.info("AdminController updateOrder method - Exit =>order(object/null): "+ order);
	    }
		catch(IllegalArgumentException e) {
			LOGGER.info("AdminController updateOrder method - Exit =>orders: "+order);
			return "redirect:/admin/error";
		}
	    return "admin-update-journeyFoodOrder";
	}
	
	@PostMapping("/order/update/{id}")
	public String updateOrder( @Valid @ModelAttribute("order") CreateJourneyFoodOrderFormData order, BindingResult result, @PathVariable("id") long id) {
	    if (result.hasErrors()) {
	    	LOGGER.error("AdminController updateOrder method - Error occured");
            return "admin-update-journeyFoodOrder";
	    }
	    journeyFoodServiceImpl.updateOrderAdmin(order);
	    LOGGER.error("AdminController updateOrder method - Exit");
	    return "redirect:/admin/fetchAllJourneyFoodOrder";
	}
	
	@GetMapping("/user/block/{id}")
	public String blockeUser(@PathVariable("id") long id, Model model) {
		LOGGER.info("AdminController blockeUser method - Enter =>id :"+id);
		try {
			userService.disableUser(id);
		    LOGGER.info("AdminController blockeUser method - Exit successful");
	    }
		catch(IllegalArgumentException e) {
			LOGGER.error("AdminController blockeUser method - Exit"+ e.getMessage());
		}
	    return "redirect:/admin/fetchAllUsers";
	}
	
	@GetMapping("/user/unblock/{id}")
	public String unblockeUser(@PathVariable("id") long id, Model model) {
		LOGGER.info("AdminController unblockeUser method - Enter =>id :"+id);
		try {
			userService.enableUser(id);
		    LOGGER.info("AdminController unblockeUser method - Exit successful");
	    }
		catch(IllegalArgumentException e) {
			LOGGER.error("AdminController unblockeUser method - Exit"+ e.getMessage());
		}
	    return "redirect:/admin/fetchAllUsers";
	}
	
	@GetMapping("/user/view/{id}")
	public String viewUser(@PathVariable("id") long id, Model model) {
		LOGGER.info("AdminController viewUser method - Enter =>id :"+id);
		UserEntity user =null;
	    try {
	    	user = userService.getUser(id);
	    	model.addAttribute("user", user);
		    LOGGER.info("AdminController viewUser method - Exit =>user(object/null): "+ user);
	    }
		catch(IllegalArgumentException e) {
			LOGGER.info("AdminController viewUser method - Exit =>user: "+id);
		}
	    return "fetchUser";
	}
	
	@GetMapping("/user/edit/{id}")
	public String updateUser(@PathVariable("id") long id, Model model) {
		LOGGER.info("AdminController updateUser method - Enter =>id :"+id);
		UserEntity user =null;
	    try {
	    	user = userService.getUser(id);
	    	model.addAttribute("user", user);
		    LOGGER.info("AdminController updateUser method - Exit =>user(object/null): "+ user);
	    }
		catch(IllegalArgumentException e) {
			LOGGER.info("AdminController updateUser method - Exit =>user: "+id);
			 return "redirect:/admin/error";
		}
	    return "update-user";
	}
	
	@PostMapping("/user/update/{id}")
	public String updateUser( @Valid @ModelAttribute("user") UserUpdateForm user, BindingResult result, @PathVariable("id") long id) {
		LOGGER.info("AdminController updateUser method - Enter");
	    if (result.hasErrors()) {
	    	LOGGER.error("AdminController updateUser method - Error occured");
            return "update-user";
	    }
	    userService.updateUser(user);
	    LOGGER.info("AdminController updateUser method - Exit");
	    return "redirect:/admin/fetchAllUsers";
	}
}
