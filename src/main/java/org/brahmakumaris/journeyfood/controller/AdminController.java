package org.brahmakumaris.journeyfood.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.brahmakumaris.journeyfood.entity.AggregateJourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.OrderForBreadDepartment;
import org.brahmakumaris.journeyfood.entity.OrderForKitchenDepartment;
import org.brahmakumaris.journeyfood.entity.OrderForRotiDepartment;
import org.brahmakumaris.journeyfood.entity.SpecialItem;
import org.brahmakumaris.journeyfood.entity.SpecialItemForADate;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.AddSpecialItems;
import org.brahmakumaris.journeyfood.order.web.CreateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.order.web.CreateOrderForBreadDepartment;
import org.brahmakumaris.journeyfood.order.web.CreateOrderForKitchenDepartment;
import org.brahmakumaris.journeyfood.order.web.CreateOrderForRotiDepartment;
import org.brahmakumaris.journeyfood.order.web.SubmitFetchOrdersFromDate2EndDate;
import org.brahmakumaris.journeyfood.order.web.SubmitFetchOrdersFromDate2EndDateOrderStatus;
import org.brahmakumaris.journeyfood.order.web.SubmitFetchTotalQuantityModelByDate;
import org.brahmakumaris.journeyfood.order.web.SubmitFetchTotalQuantityModelByDateForASlot;
import org.brahmakumaris.journeyfood.order.web.UserUpdateForm;
import org.brahmakumaris.journeyfood.security.UserService;
import org.brahmakumaris.journeyfood.security.exceptions.OrderNotFoundException;
import org.brahmakumaris.journeyfood.security.exceptions.UserNotFoundException;
import org.brahmakumaris.journeyfood.service.JourneyFoodService;
import org.brahmakumaris.journeyfood.service.OrderBreadDeptService;
import org.brahmakumaris.journeyfood.service.OrderKitchenDeptService;
import org.brahmakumaris.journeyfood.service.OrderRotiDeptService;
import org.brahmakumaris.journeyfood.service.SpecialItemForADateService;
import org.brahmakumaris.journeyfood.service.SpecialItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private JourneyFoodService journeyFoodServiceImpl;
	
	@Autowired
	private OrderKitchenDeptService orderKitchenDeptService;
	
	@Autowired
	private OrderBreadDeptService orderBreadDeptService;
	
	@Autowired
	private OrderRotiDeptService orderRotiDeptService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SpecialItemForADateService specialItemForADateService;
	
	@Autowired
	private SpecialItemService specialItemService;
	
	@GetMapping("/error")
    public String error() {
        return "error";
    }
	
	@GetMapping("/fetchAllJourneyFoodOrder")
    public String fetchAllJourneyFoodOrder(Model model) {
		LOGGER.info("AdminController fetchAllJourneyFoodOrder method - Enter");
		return paginateAllOrder(1, model);
    }
	
	@GetMapping("/fetchAllJourneyFoodOrder/{pageNo}")
    public String paginateAllOrder(@PathVariable(value="pageNo") int pageNo, Model model) {
		int pageSize=8;
		LOGGER.info("AdminController paginateAllOrder method - Enter");
	 	Page<JourneyFoodOrder> page= journeyFoodServiceImpl.findPaginated(pageNo, pageSize);
	 	model.addAttribute("orders", page.isEmpty()?null:page);
	 	model.addAttribute("title", "Show All Orders");
	 	model.addAttribute("currentPage", pageNo);
	 	model.addAttribute("totalPages", page.getTotalPages());
	 	model.addAttribute("url","/admin/fetchAllJourneyFoodOrder/");
	 	LOGGER.info("AdminController paginateAllOrder method - Exit =>orders-isEmpty()->: "+page.getContent().isEmpty());
        return "fetchAllJourneyOrdersByAdmin";
    }
	
	@GetMapping("/fetchAllJourneyFoodOrdersNotDisabled")
    public String fetchAllJourneyFoodOrdersNotDisabled(Model model) {
		LOGGER.info("AdminController fetchAllJourneyFoodOrdersNotDisabled method - Enter");
	 	List <JourneyFoodOrder> orders=journeyFoodServiceImpl.getAllPlacedOrders();
	 	model.addAttribute("orders", orders.isEmpty()?null:orders);
	 	model.addAttribute("title", "Show All Placed Orders");
	 	model.addAttribute("url","/admin/fetchAllJourneyFoodOrdersNotDisabled/");
	 	System.out.println("User data in Journeyfood UserId :-> "+orders.get(0).getUser().getUserId());
	 	LOGGER.info("AdminController fetchAllJourneyFoodOrdersNotDisabled method - Exit =>orders-isEmpty()->: "+orders.isEmpty());
		return "fetchAllPlacedJourneyOrdersByAdmin";
//		return paginateAllPlacedOrder(1, model);
    }
	
	@GetMapping("/fetchAllJourneyFoodOrdersNotDisabled/{pageNo}")
    public String paginateAllPlacedOrder(@PathVariable(value="pageNo") int pageNo, Model model) {
		LOGGER.info("AdminController paginateAllPlacedOrder method - Enter");
		int pageSize=8;
	 	Page<JourneyFoodOrder> page=journeyFoodServiceImpl.getPaginatedPlacedOrdersByUser(pageNo, pageSize);
	 	model.addAttribute("orders", page.isEmpty()?null:page);
	 	model.addAttribute("title", "Show All Placed Orders");
	 	model.addAttribute("currentPage", pageNo);
	 	model.addAttribute("totalPages", page.getTotalPages());
	 	model.addAttribute("url","/admin/fetchAllJourneyFoodOrdersNotDisabled/");
	 	LOGGER.info("AdminController paginateAllPlacedOrder method - Exit =>orders-isEmpty()->: "+page.getContent().isEmpty());
        return "fetchAllPlacedJourneyOrdersByAdmin";
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
	
	@GetMapping("/fetchAllPlacedOrdersForADateInASlot")
	public String fetchAllPlacedOrdersForADateInASlot(SubmitFetchTotalQuantityModelByDateForASlot submitFetchTotalQuantityModelByDateForASlot) {
		return "getAllPlacedOrderForADateAndSlot";
	}
	
	@PostMapping("/fetchAllPlacedOrdersForADateInASlot")
    public String fetchAllPlacedOrdersForADateInASlot( @Valid @ModelAttribute("submitFetchTotalQuantityModelByDateForASlot") SubmitFetchTotalQuantityModelByDateForASlot submitFetchTotalQuantityModelByDateForASlot
    		, BindingResult result, Model model) {
		if (result.hasErrors()) {
	    	LOGGER.error("AdminController updateOrder method - Error occured");
            return "getAllPlacedOrderForADateAndSlot";
	    }
		LOGGER.info("AdminController fetchAllPlacedOrdersForADate method - Enter");
		List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrdersByDateAndSlot(submitFetchTotalQuantityModelByDateForASlot.getMealRetrievalDate(), submitFetchTotalQuantityModelByDateForASlot.getMealRetrievalTime(), "PLACED");
	 	model.addAttribute("orders", orders.isEmpty()?null:orders);
	 	model.addAttribute("date", submitFetchTotalQuantityModelByDateForASlot==null?null:submitFetchTotalQuantityModelByDateForASlot.getMealRetrievalDate());
	 	LOGGER.info("AdminController fetchAllPlacedOrdersForADate method - Exit =>orders: "+orders);
        return "showPlacedOrdersByDateForASlot";
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
    public ModelAndView fetchAll() {
		LOGGER.info("AdminController fetchAllUsers method - Enter");
	 	List<UserEntity> users=userService.getUsers();
	 	LOGGER.info("AdminController fetchAllUsers method - Exit =>users: "+users);
        return new ModelAndView("fetchAllUsers", "users", users.isEmpty()?null:users);
    }
	
	@GetMapping("/fetchAllUsersByName")
    public ModelAndView fetchAllUsers(@RequestParam Optional<String>name) {
		LOGGER.info("AdminController fetchAllUsers method - Enter");
	 	List<UserEntity> users=userService.getUsersByName(name.orElse("_"));
	 	LOGGER.info("AdminController fetchAllUsers method - Exit =>users: "+users);
        return new ModelAndView("fetchAllUsers", "users", users.isEmpty()?null:users);
    }
	
	@GetMapping("/order/delete/{id}")
	public String deleteOrder(@PathVariable("id") long id, RedirectAttributes redirectAttributes, Model model) throws UnsupportedEncodingException, MessagingException {
		LOGGER.info("AdminController deleteOrder method - Enter =>id :"+id);
		try {
			journeyFoodServiceImpl.delete(id);
		    LOGGER.info("AdminController deleteOrder method - Exit successful");
		    redirectAttributes.addFlashAttribute("message", "Order : "+journeyFoodServiceImpl.findByOrderId(id)+" updated successfully");
	        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
	    }
		catch(IllegalArgumentException e) {
			LOGGER.error("AdminController deleteOrder method - Exit"+ e.getMessage());
		}
	    return "redirect:/admin/fetchAllJourneyFoodOrder";
	}
	
	@GetMapping("/order/delivered/{id}")
	public String deliveredOrder(@PathVariable("id") long id, RedirectAttributes redirectAttributes, Model model) throws UnsupportedEncodingException, MessagingException {
		LOGGER.info("AdminController updateOrder method - Enter =>id :"+id);
		JourneyFoodOrder order =null;
	    try {
	    	journeyFoodServiceImpl.orderCompleted(id);
		    LOGGER.info("AdminController updateOrder method - Exit =>order(object/null): "+ order);
		    redirectAttributes.addFlashAttribute("message", "Order : "+journeyFoodServiceImpl.findByOrderId(id).getOrderId()+" delivered successfully");
	        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
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
	public String updateOrder( @Valid @ModelAttribute("order") CreateJourneyFoodOrderFormData order, BindingResult result,
			RedirectAttributes redirectAttributes, @PathVariable("id") long id) throws UnsupportedEncodingException, MessagingException {
	    if (result.hasErrors()) {
	    	LOGGER.error("AdminController updateOrder method - Error occured");
            return "admin-update-journeyFoodOrder";
	    }
	    journeyFoodServiceImpl.updateOrderAdmin(order);
	    LOGGER.error("AdminController updateOrder method - Exit");
	    redirectAttributes.addFlashAttribute("message", "Order : "+order.getOrderId()+" updated successfully");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
	    return "redirect:/admin/fetchAllJourneyFoodOrder";
	}
	
	@GetMapping("/user/block/{id}")
	public String blockeUser(@PathVariable("id") long id, RedirectAttributes redirectAttributes, Model model) {
		LOGGER.info("AdminController blockeUser method - Enter =>id :"+id);
		try {
			userService.disableUser(id);
		    LOGGER.info("AdminController blockeUser method - Exit successful");
		    redirectAttributes.addFlashAttribute("message", "User "+userService.getUser(id).getNameOfGuide()+" blocked successfully");
	        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
	    }
		catch(IllegalArgumentException e) {
			LOGGER.error("AdminController blockeUser method - Exit"+ e.getMessage());
		}
	    return "redirect:/admin/fetchAllUsers";
	}
	
	@GetMapping("/user/unblock/{id}")
	public String unblockeUser(@PathVariable("id") long id, RedirectAttributes redirectAttributes, Model model) {
		LOGGER.info("AdminController unblockeUser method - Enter =>id :"+id);
		try {
			userService.enableUser(id);
		    LOGGER.info("AdminController unblockeUser method - Exit successful");
		    redirectAttributes.addFlashAttribute("message", "User "+userService.getUser(id).getNameOfGuide()+" unblocked successfully");
	        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
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
	public String updateUser( @Valid @ModelAttribute("user") UserUpdateForm user, BindingResult result, RedirectAttributes redirectAttributes,
			@PathVariable("id") long id) throws IllegalArgumentException, UnsupportedEncodingException, MessagingException {
		LOGGER.info("AdminController updateUser method - Enter");
	    if (result.hasErrors()) {
	    	LOGGER.error("AdminController updateUser method - Error occured");
            return "update-user";
	    }
	    userService.updateUser(user);
	    LOGGER.info("AdminController updateUser method - Exit");
	    redirectAttributes.addFlashAttribute("message", "User "+userService.getUser(id).getNameOfGuide()+" profile data updated successfully");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
	    return "redirect:/admin/fetchAllUsers";
	}
	
	@GetMapping("/addSpecialItem")
	public String addSpecialItem(SpecialItem specialItem) {
		return "addSpecialItem";
	}
	
	@PostMapping("/addSpecialItem")
	public String addSpecialItem(@Valid @ModelAttribute("specialItem") SpecialItem specialItem, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		LOGGER.info("======Admin addSpecialItem method - Start=====");
	 if (result.hasErrors()) {
		 	System.out.println("************************ERROR******************************");
		 	System.out.println(specialItem.getItem());
	    	LOGGER.error("AdminController addSpecialItem method - Error occured");
            return "addSpecialItem";
	    }
	 	System.out.println("==================================================================");
	 	System.out.println(specialItem.getItem());
	 	model.addAttribute("specialItems",(specialItemService.addItem(specialItem)));
        redirectAttributes.addFlashAttribute("message", "Special Items added successfully");
	    LOGGER.info("AdminController addSpecialItem method - Exit");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/admin/fetchAllSpecialItem";
	}
	
	@GetMapping("/fetchAllSpecialItem")
	public String fetchAllSpecialItem(Model model) {
		LOGGER.info("======AdminController addSpecialItem method - Start=======");
		model.addAttribute("items", specialItemService.getItems());
		LOGGER.info("======AdminController addSpecialItem method - Exit=======");
		return "fetchAllSpecialItem";
	}

	@GetMapping("/item/edit/{itemId}")
	public String updateItem(@PathVariable("itemId")Integer itemId, Model model) {
		LOGGER.info("AdminController updateItem method - Enter =>itemId :"+itemId);
		SpecialItem item =null;
	    try {
	    	item = specialItemService.getItem(itemId);
	    	model.addAttribute("specialItem", item);
		    LOGGER.info("AdminController updateItem method - Exit =>item(object/null): "+ item);
	    }
		catch(IllegalArgumentException e) {
			LOGGER.info("AdminController updateItem method - Exit =>item: "+itemId+" ,Error - "+e.getMessage());
			 return "redirect:/admin/error";
		}
	    return "update-item";
	}
	
	@PostMapping("/item/update/{itemId}")
	public String updateItem( @Valid @ModelAttribute("specialItem") SpecialItem specialItem, BindingResult result, RedirectAttributes redirectAttributes,
			@PathVariable("itemId")Integer itemId) throws IllegalArgumentException, UnsupportedEncodingException, MessagingException {
		LOGGER.info("AdminController updateItem method - Enter");
	    if (result.hasErrors()) {
	    	LOGGER.error("AdminController updateItem method - Error occured");
            return "update-item";
	    }
	    specialItemService.update(specialItem);
	    LOGGER.info("AdminController updateItem method - Exit");
	    redirectAttributes.addFlashAttribute("message", "Special item with ID: "+specialItem.getItemId()+" is updated successfully");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
	    return "redirect:/admin/fetchAllSpecialItem";
	}
	
	@GetMapping("/item/delete/{itemId}")
	public String removeItem(@PathVariable("itemId") int id, RedirectAttributes redirectAttributes, Model model) {
		LOGGER.info("AdminController removeItem method - Enter =>id :"+id);
		try {
			specialItemService.delete(id);
		    LOGGER.info("AdminController removeItem method - Exit successful");
		    redirectAttributes.addFlashAttribute("message", "Special Item with ID: "+id+" deleted successfully");
	        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
	    }
		catch(IllegalArgumentException e) {
			LOGGER.error("AdminController removeItem method - Exit"+ e.getMessage());
		}
	    return "redirect:/admin/fetchAllSpecialItem";
	}
	
	@GetMapping("/addSpecialItems")
	public String addSpecialItems(AddSpecialItems addSpecialItems) {
		return "addSpecialItems";
	}
	
	@PostMapping("/addSpecialItems")
	public String addSpecialItems(@Valid @ModelAttribute("addSpecialItems") AddSpecialItems addSpecialItems, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		LOGGER.info("======Admin addSpecialItems method - Start=====");
	 if (result.hasErrors()) {
		 	System.out.println("************************ERROR******************************");
		 	System.out.println(addSpecialItems.getSpecialItems());
	    	LOGGER.error("AdminController addSpecialItems method - Error occured");
            return "addSpecialItems";
	    }
	 	System.out.println("==================================================================");
	 	System.out.println(addSpecialItems.getSpecialItems());
	 	SpecialItemForADate specialItemForADate = specialItemForADateService.addItem(addSpecialItems);
	 	model.addAttribute("specialItems", specialItemForADate);
        redirectAttributes.addFlashAttribute("message", "Special Items added successfully");
	    LOGGER.info("AdminController addSpecialItems method - Exit");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/admin/fetchAllSpecialItems";
	}
	
	@GetMapping("/fetchAllSpecialItems")
	public String fetchAllSpecialItems(Model model) {
		LOGGER.info("======AdminController addSpecialItems method - Start=======");
		model.addAttribute("items", specialItemForADateService.getItems());
		LOGGER.info("======AdminController addSpecialItems method - Exit=======");
		return "fetchAllSpecialItems";
	}
	
	@GetMapping("/specialItem/edit/{id}")
	public String updateSpecialItem(@PathVariable("id") long id, Model model) {
		LOGGER.info("AdminController updateSpecialItem method - Enter =>id :"+id);
		SpecialItemForADate item =null;
	    try {
	    	item = specialItemForADateService.getItem(id);
	    	model.addAttribute("item", item);
		    LOGGER.info("AdminController updateSpecialItem method - Exit =>item(object/null): "+ item);
	    }
		catch(IllegalArgumentException e) {
			LOGGER.info("AdminController updateSpecialItem method - Exit =>item: "+id);
			 return "redirect:/admin/error";
		}
	    return "update-specialItem";
	}
	
	@PostMapping("/specialItem/edit/{id}")
	public String updateSpecialItem( @Valid @ModelAttribute("item") AddSpecialItems items, BindingResult result, RedirectAttributes redirectAttributes,
			@PathVariable("id") long id) throws IllegalArgumentException, UnsupportedEncodingException, MessagingException {
		LOGGER.info("AdminController updateSpecialItem method - Enter");
	    if (result.hasErrors()) {
	    	LOGGER.error("AdminController updateSpecialItem method - Error occured");
            return "update-specialItem";
	    }
	    specialItemForADateService.update(items);
	    LOGGER.info("AdminController updateSpecialItem method - Exit");
	    redirectAttributes.addFlashAttribute("message", "Special item with ID: "+items.getId()+" is updated successfully");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
	    return "redirect:/admin/fetchAllSpecialItems";
	}
	
	@GetMapping("/specialItem/delete/{id}")
	public String removeSpecialItem(@PathVariable("id") long id, RedirectAttributes redirectAttributes, Model model) {
		LOGGER.info("AdminController removeSpecialItem method - Enter =>id :"+id);
		try {
			specialItemForADateService.delete(id);
		    LOGGER.info("AdminController removeSpecialItem method - Exit successful");
		    redirectAttributes.addFlashAttribute("message", "Special Item with ID: "+id+" deleted successfully");
	        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
	    }
		catch(IllegalArgumentException e) {
			LOGGER.error("AdminController removeSpecialItem method - Exit"+ e.getMessage());
		}
	    return "redirect:/admin/fetchAllSpecialItems";
	}
	
	@GetMapping("/addOrderForBreadDepartment")
	public String addOrderForBreadDepartment(CreateOrderForBreadDepartment createOrderForBreadDepartment) {
		return "order-breadDept";
	}
	
	@PostMapping("/addOrderForBreadDepartment")
	public String addOrderForBreadDepartment(@Valid @ModelAttribute("createOrderForBreadDepartment") CreateOrderForBreadDepartment createOrderForBreadDepartment, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		LOGGER.info("======Admin addOrderForBreadDepartment method - Start=====");
	 if (result.hasErrors()) {
		 	LOGGER.error("************************ERROR******************************");
//		 	LOGGER.error(createOrderForBreadDepartment.getItem());
	    	LOGGER.error("AdminController addOrderForBreadDepartment method - Error occured");
            return "order-breadDept";
	    }
	 	System.out.println("==================================================================");
	 	model.addAttribute("order",(orderBreadDeptService.createOrder(createOrderForBreadDepartment)));
        redirectAttributes.addFlashAttribute("message", "Order for Bread Department added successfully");
	    LOGGER.info("AdminController addOrderForBreadDepartment method - Exit");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/admin/fetchOrdersForBreadDept";
	}
	
	@GetMapping("/editOrderForBreadDepartment/{breadOrderId}")
	public String editOrderForBreadDepartment(@PathVariable("breadOrderId") long id, Model model) {
		LOGGER.info("AdminController editOrderForBreadDepartment method - Enter =>id :"+id);
		OrderForBreadDepartment order = null;
	    try {
	    	order = orderBreadDeptService.getOrder(id);
	    	CreateOrderForBreadDepartment createOrderForBreadDepartment = new CreateOrderForBreadDepartment();
	    	createOrderForBreadDepartment.setBreadOrderId(order.getBreadOrderId());
	    	createOrderForBreadDepartment.setBread(order.getBread());
	    	createOrderForBreadDepartment.setOrderDate(order.getOrderDate());
	    	createOrderForBreadDepartment.setOrderForDate(order.getOrderForDate());
	    	createOrderForBreadDepartment.setSlicedBread(order.getSlicedBread());
	    	createOrderForBreadDepartment.setSlot(order.getSlot());
	    	model.addAttribute("order", createOrderForBreadDepartment);
		    LOGGER.info("AdminController editOrderForBreadDepartment method - Exit =>order(object/null): "+ order);
	    }
		catch(IllegalArgumentException e) {
			LOGGER.info("AdminController editOrderForBreadDepartment method - Exit =>Order Id: "+id);
			 return "redirect:/admin/error";
		}
	    return "edit-order-breadDept";
	}
	
	@PostMapping("/editOrderForBreadDepartment/{breadOrderId}")
	public String editOrderForBreadDepartment( @Valid @ModelAttribute("order") CreateOrderForBreadDepartment order, BindingResult result
			, RedirectAttributes redirectAttributes) throws IllegalArgumentException, UnsupportedEncodingException, MessagingException {
		LOGGER.info("AdminController editOrderForBreadDepartment method - Enter");
	    if (result.hasErrors()) {
	    	LOGGER.error("AdminController editOrderForBreadDepartment method - Error occured");
            return "edit-order-breadDept";
	    }
	    orderBreadDeptService.updateOrder(order);
	    LOGGER.info("AdminController editOrderForBreadDepartment method - Exit");
	    redirectAttributes.addFlashAttribute("message", "Order: "+order.getBreadOrderId()+"  updated successfully");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
	    return "redirect:/admin/fetchOrdersForBreadDept";
	}
	
	@GetMapping("/fetchOrdersForBreadDept")
	public String fetchOrdersForBreadDept(Model model) {
		model.addAttribute("orders", orderBreadDeptService.getAllOrder());
		return "fetchOrdersForBreadDept";
	}
	
	@GetMapping("/addOrderForKitchenDepartment")
	public String addOrderForKitchenDepartment(CreateOrderForKitchenDepartment createOrderForKitchenDepartment) {
		return "order-kitchenDept";
	}
	
	@PostMapping("/addOrderForKitchenDepartment")
	public String addOrderForKitchenDepartment(@Valid @ModelAttribute("createOrderForKitchenDepartment") CreateOrderForKitchenDepartment createOrderForKitchenDepartment, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		LOGGER.info("======Admin addOrderForKitchenDepartment method - Start=====");
	 if (result.hasErrors()) {
		 	LOGGER.error("************************ERROR******************************");
		 	LOGGER.error(createOrderForKitchenDepartment.toString());
	    	LOGGER.error("AdminController addOrderForKitchenDepartment method - Error occured");
            return "order-kitchenDept";
	    }
	 	System.out.println("==================================================================");
	 	model.addAttribute("order",(orderKitchenDeptService.createOrder(createOrderForKitchenDepartment)));
        redirectAttributes.addFlashAttribute("message", "Order added successfully");
	    LOGGER.info("AdminController addOrderForKitchenDepartment method - Exit");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/admin/fetchOrdersForKitchenDept";
	}
	
	@GetMapping("/editOrderForKitchenDepartment/{kitchenDeptId}")
	public String editOrderForKitchenDepartment(@PathVariable("kitchenDeptId")Long kitchenDeptId, Model model) {
		LOGGER.info("======Admin editOrderForKitchenDepartment GET method - Start=====");
		OrderForKitchenDepartment order = orderKitchenDeptService.getOrder(kitchenDeptId);
		CreateOrderForKitchenDepartment createOrderForKitchenDepartment = new CreateOrderForKitchenDepartment();
		createOrderForKitchenDepartment.setChips(order.getChips());
		createOrderForKitchenDepartment.setChutney(order.getChutney());
		createOrderForKitchenDepartment.setCurdRice(order.getCurdRice());
		createOrderForKitchenDepartment.setIdli(order.getIdli());
		createOrderForKitchenDepartment.setKitchenOrderId(order.getKitchenDeptId());
		createOrderForKitchenDepartment.setOrderForDate(order.getOrderForDate());
		createOrderForKitchenDepartment.setSlot(order.getSlot());
		createOrderForKitchenDepartment.setSoojiDhokla(order.getSoojiDhokla());
		createOrderForKitchenDepartment.setTomatoRice(order.getTomatoRice());
		model.addAttribute("order", createOrderForKitchenDepartment);
		LOGGER.info("======Admin editOrderForKitchenDepartment GET method - End=====");
		return "edit-order-kitchenDept";
	}
	
	@PostMapping("/editOrderForKitchenDepartment")
	public String editOrderForKitchenDepartment(@Valid @ModelAttribute("order") CreateOrderForKitchenDepartment order, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		LOGGER.info("======Admin editOrderForKitchenDepartment POST method - Start=====");
	 if (result.hasErrors()) {
		 	LOGGER.error("*********************************ERROR***********************************");
		 	LOGGER.error(order.toString());
	    	LOGGER.error("AdminController editOrderForKitchenDepartment POST method - Error occured");
            return "edit-order-kitchenDept";
	    }
	 	System.out.println("==================================================================");
	 	model.addAttribute("order",(orderKitchenDeptService.createOrder(order)));
        redirectAttributes.addFlashAttribute("message", "Order added successfully");
	    LOGGER.info("AdminController editOrderForKitchenDepartment POST method - Exit");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/admin/fetchOrdersForKitchenDept";
	}
	
	@GetMapping("/fetchOrdersForKitchenDept")
	public String fetchOrdersForKitchenDept(Model model) {
		model.addAttribute("orders", orderKitchenDeptService.getAllOrder());
		return "fetchOrdersForKitchenDept";
	}
	
	@GetMapping("/addOrderForRotiDepartment")
	public String addOrderForRotiDepartment(CreateOrderForRotiDepartment createOrderForRotiDepartment) {
		return "order-rotiDept";
	}
	
	@PostMapping("/addOrderForRotiDepartment")
	public String addOrderForRotiDepartment(@Valid @ModelAttribute("createOrderForRotiDepartment") CreateOrderForRotiDepartment createOrderForRotiDepartment, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		LOGGER.info("======Admin addOrderForRotiDepartment method - Start=====");
	 if (result.hasErrors()) {
		 	LOGGER.error("************************ERROR******************************");
		 	LOGGER.error(createOrderForRotiDepartment.toString());
	    	LOGGER.error("AdminController addOrderForRotiDepartment method - Error occured");
            return "order-rotiDept";
	    }
	 	System.out.println("==================================================================");
	 	model.addAttribute("order",(orderRotiDeptService.createOrder(createOrderForRotiDepartment)));
        redirectAttributes.addFlashAttribute("message", "Order added successfully");
	    LOGGER.info("AdminController addOrderForRotiDepartment method - Exit");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/admin/fetchOrdersForRotiDept";
	}
	
	@GetMapping("/editOrderForRotiDepartment/{rotiDeptId}")
	public String editOrderForRotiDepartment(@PathVariable("rotiDeptId")Long rotiDeptId, Model model) {
		LOGGER.info("======Admin editOrderForRotiDepartment GET method - Start=====");
		OrderForRotiDepartment orderForRotiDepartment = orderRotiDeptService.getOrder(rotiDeptId);
		CreateOrderForRotiDepartment createOrderForRotiDepartment = new CreateOrderForRotiDepartment();
		LOGGER.info("Order for Roti Dept- "+orderForRotiDepartment);
		createOrderForRotiDepartment.setRotiDeptId(orderForRotiDepartment.getRotiDeptId());
		createOrderForRotiDepartment.setOrderDate(orderForRotiDepartment.getOrderDate());
		createOrderForRotiDepartment.setOrderForDate(orderForRotiDepartment.getOrderForDate());
		createOrderForRotiDepartment.setPuri(orderForRotiDepartment.getPuri());
		createOrderForRotiDepartment.setRoti(orderForRotiDepartment.getRoti());
		createOrderForRotiDepartment.setSandwich(orderForRotiDepartment.getSandwich());
		createOrderForRotiDepartment.setSlot(orderForRotiDepartment.getSlot());
		createOrderForRotiDepartment.setThepla(orderForRotiDepartment.getThepla());
		LOGGER.info("Order Model for Roti Dept- "+createOrderForRotiDepartment);
		model.addAttribute("order", createOrderForRotiDepartment);
		LOGGER.info("======Admin editOrderForRotiDepartment GET method - End=====");
		return "edit-order-rotiDept";
	}
	
	@PostMapping("/editOrderForRotiDepartment")
	public String editOrderForRotiDepartment(@Valid @ModelAttribute("order") CreateOrderForRotiDepartment order, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		LOGGER.info("======Admin editOrderForRotiDepartment POST method - Start=====");
	 if (result.hasErrors()) {
		 	LOGGER.error("*********************************ERROR***********************************");
		 	LOGGER.error(order.toString());
	    	LOGGER.error("AdminController editOrderForRotiDepartment POST method - Error occured");
            return "edit-order-rotiDept";
	    }
	 	System.out.println("==================================================================");
	 	model.addAttribute("order",(orderRotiDeptService.updateOrder(order)));
        redirectAttributes.addFlashAttribute("message", "Order added successfully");
	    LOGGER.info("AdminController editOrderForRotiDepartment POST method - Exit");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/admin/fetchOrdersForRotiDept";
	}
	
	@GetMapping("/fetchOrdersForRotiDept")
	public String fetchOrdersForRotiDept(Model model) {
		model.addAttribute("orders", orderRotiDeptService.getAllOrder());
		return "fetchOrdersForRotiDept";
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