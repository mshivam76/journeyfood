package org.brahmakumaris.journeyfood.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
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
import org.brahmakumaris.journeyfood.security.exceptions.OrderNotFoundException;
import org.brahmakumaris.journeyfood.security.exceptions.UserNotFoundException;
import org.brahmakumaris.journeyfood.service.JourneyFoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private JourneyFoodService journeyFoodServiceImpl;
	
	@Autowired
	private UserService userService;
	
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
		LOGGER.info("AdminController fetchFromDate2EndDateWithOrderStatusOrders method - Enter");
		List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrdersByDateRangeAndOrderStatus(submitFetchOrdersFromDate2EndDateOrderStatus.getStartDate(),submitFetchOrdersFromDate2EndDateOrderStatus.getEndDate(),submitFetchOrdersFromDate2EndDateOrderStatus.getOrderStatus());
	 	model.addAttribute("orders", orders.isEmpty()?null:orders);
	 	model.addAttribute("date", submitFetchOrdersFromDate2EndDateOrderStatus==null?null:"Orders from "+submitFetchOrdersFromDate2EndDateOrderStatus.getStartDate()+" to "+submitFetchOrdersFromDate2EndDateOrderStatus.getEndDate());
	 	model.addAttribute("startDate", submitFetchOrdersFromDate2EndDateOrderStatus==null?null:submitFetchOrdersFromDate2EndDateOrderStatus.getStartDate());
	 	model.addAttribute("endDate", submitFetchOrdersFromDate2EndDateOrderStatus==null?null:submitFetchOrdersFromDate2EndDateOrderStatus.getEndDate());
	 	model.addAttribute("orderStatus", submitFetchOrdersFromDate2EndDateOrderStatus==null?null:submitFetchOrdersFromDate2EndDateOrderStatus.getOrderStatus());
	 	LOGGER.info("AdminController fetchFromDate2EndDateWithOrderStatusOrders method - Exit =>orders: "+orders);
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
		LOGGER.info("AdminController fetchFromDate2EndDateOrders method - Enter");
		List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrdersByDateRange(submitFetchOrdersFromDate2EndDate.getStartDate(),submitFetchOrdersFromDate2EndDate.getEndDate());
	 	model.addAttribute("orders", orders.isEmpty()?null:orders);
	 	model.addAttribute("date", submitFetchOrdersFromDate2EndDate==null?null:"Orders from "+submitFetchOrdersFromDate2EndDate.getStartDate()+" to "+submitFetchOrdersFromDate2EndDate.getEndDate());
	 	model.addAttribute("startDate", submitFetchOrdersFromDate2EndDate==null?null:submitFetchOrdersFromDate2EndDate.getStartDate());
	 	model.addAttribute("endDate", submitFetchOrdersFromDate2EndDate==null?null:submitFetchOrdersFromDate2EndDate.getEndDate());
	 	LOGGER.info("AdminController fetchFromDate2EndDateOrders method - Exit =>orders: "+orders);
        return "showOrdersStartDate2EndDate";
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
		LOGGER.info("AdminController fetchAllPlacedOrdersForADate method - Enter");
		List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrdersByDate(submitFetchTotalQuantityModelByDate.getMealRetrievalDate(), "PLACED");
	 	 model.addAttribute("orders", orders.isEmpty()?null:orders);
	 	model.addAttribute("date", submitFetchTotalQuantityModelByDate==null?null:submitFetchTotalQuantityModelByDate.getMealRetrievalDate());
	 	LOGGER.info("AdminController fetchAllPlacedOrdersForADate method - Exit =>orders: "+orders);
        return "showPlacedOrdersByDate";
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
		LOGGER.info("AdminController fetchAllCanceledOrdersForADate method - Enter");
		List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrdersByDate(submitFetchTotalQuantityModelByDate.getMealRetrievalDate(),"CANCELLED");
	 	model.addAttribute("orders", orders.isEmpty()?null:orders);
	 	model.addAttribute("date", submitFetchTotalQuantityModelByDate==null?null:submitFetchTotalQuantityModelByDate.getMealRetrievalDate());
	 	LOGGER.info("AdminController fetchAllCanceledOrdersForADate method - Exit =>orders: "+orders);
        return "showCanceledOrdersByDate";
    }
	
	@GetMapping("/fetchDeliveredOrdersForADate")
	public String fetchDeliveredOrdersForADate(SubmitFetchTotalQuantityModelByDate submitFetchTotalQuantityModelByDate) {
		return "getAllDeliveredOrdersForADate";
	}
	
	@PostMapping("/fetchDeliveredOrdersForADate")
    public String fetchDeliveredOrdersForADate( @Valid @ModelAttribute("submitFetchTotalQuantityModelByDate") SubmitFetchTotalQuantityModelByDate submitFetchTotalQuantityModelByDate
    		, BindingResult result, Model model) {
		if (result.hasErrors()) {
	    	LOGGER.error("AdminController updateOrder method - Error occured");
            return "getAllDeliveredOrdersForADate";
	    }
		LOGGER.info("AdminController fetchDeliveredOrdersForADate method - Enter");
		List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrdersByDate(submitFetchTotalQuantityModelByDate.getMealRetrievalDate(),"DELIVERED");
	 	model.addAttribute("orders", orders.isEmpty()?null:orders);
	 	model.addAttribute("date", submitFetchTotalQuantityModelByDate==null?null:submitFetchTotalQuantityModelByDate.getMealRetrievalDate());
	 	LOGGER.info("AdminController fetchDeliveredOrdersForADate method - Exit =>orders: "+orders);
        return "showDelivedOrdersByDate";
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
		LOGGER.info("AdminController fetchAllOrdersForADate method - Enter");
		List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrdersByDate(submitFetchTotalQuantityModelByDate.getMealRetrievalDate());
	 	model.addAttribute("orders", orders.isEmpty()?null:orders);
	 	model.addAttribute("date", submitFetchTotalQuantityModelByDate==null?null:submitFetchTotalQuantityModelByDate.getMealRetrievalDate());
	 	LOGGER.info("AdminController fetchAllOrdersForADate method - Exit =>orders: "+orders);
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
		List <JourneyFoodOrder> orders = journeyFoodServiceImpl.getOrdersByDate(submitFetchTotalQuantityModelByDate.getMealRetrievalDate(), "PLACED");
		AggregateJourneyFoodOrder totalOrder=journeyFoodServiceImpl.getOrdersByDateAndNotDisabled(submitFetchTotalQuantityModelByDate.getMealRetrievalDate());
	 	model.addAttribute("total", totalOrder==null?null:totalOrder);
	 	model.addAttribute("orders", orders.isEmpty()?null:orders);
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
	public String deleteOrder(@PathVariable("id") long id, Model model) throws UnsupportedEncodingException, MessagingException {
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
	public String deliveredOrder(@PathVariable("id") long id, Model model) throws UnsupportedEncodingException, MessagingException {
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
	public String updateOrder( @Valid @ModelAttribute("order") CreateJourneyFoodOrderFormData order, BindingResult result, @PathVariable("id") long id) throws UnsupportedEncodingException, MessagingException {
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
	
	@GetMapping("/user/view/{userId}")
	public String viewUser(@PathVariable("userId") long userId, Model model) {
		LOGGER.info("AdminController viewUser method - Enter =>userId :"+userId);
		UserEntity user =null;
	    try {
	    	user = userService.getUser(userId);
	    	model.addAttribute("user", user);
		    LOGGER.info("AdminController viewUser method - Exit =>user(object/null): "+ user);
	    }
		catch(IllegalArgumentException e) {
			LOGGER.info("AdminController viewUser method - Exit =>user: "+userId);
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
	public String updateUser( @Valid @ModelAttribute("user") UserUpdateForm user, BindingResult result, @PathVariable("id") long id) throws IllegalArgumentException, UnsupportedEncodingException, MessagingException {
		LOGGER.info("AdminController updateUser method - Enter");
	    if (result.hasErrors()) {
	    	LOGGER.error("AdminController updateUser method - Error occured");
            return "update-user";
	    }
	    userService.updateUser(user);
	    LOGGER.info("AdminController updateUser method - Exit");
	    return "redirect:/admin/fetchAllUsers";
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(OrderNotFoundException.class)
	public ModelAndView orderNotFound(Exception e) {
		LOGGER.error("AdminController orderNotFound Exception - Enter");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("orderNotFoundError");
		mv.addObject("ex", e.getMessage());
		LOGGER.error(e.getMessage());
		return mv;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserNotFoundException.class)
	public ModelAndView userNotFound(Exception e) {
		LOGGER.error("AdminController userNotFound Exception - Enter");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("userNotFoundError");
		mv.addObject("ex", e.getMessage());
		LOGGER.error(e.getMessage());
		return mv;
	}
}
