package org.brahmakumaris.journeyfood.controller;

import java.util.List;

import javax.validation.Valid;

import org.brahmakumaris.journeyfood.entity.AggregateJourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.CreateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.order.web.SubmitFetchTotalQuantityModelByDate;
import org.brahmakumaris.journeyfood.order.web.UserUpdateForm;
import org.brahmakumaris.journeyfood.security.UserService;
import org.brahmakumaris.journeyfood.service.JourneyFoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	 	AggregateJourneyFoodOrder order=journeyFoodServiceImpl.getOrdersByDateAndNotDisabled(submitFetchTotalQuantityModelByDate.getMealRetrievalDate());
	 	 model.addAttribute("order", order==null?null:order);
	 	LOGGER.info("AdminController fetchSumOfJourneyFoodOrdersNotDisabled method - Exit =>orders: "+order);
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
		}
	    return "update-journeyFoodOrder";
	}
	
	@PostMapping("/order/update/{id}")
	public String updateOrder( @Valid @ModelAttribute("order") CreateJourneyFoodOrderFormData order, BindingResult result, @PathVariable("id") long id) {
	    if (result.hasErrors()) {
	    	LOGGER.error("AdminController updateOrder method - Error occured");
            return "update-journeyFoodOrder";
	    }
	    journeyFoodServiceImpl.updateOrder(order);
	    LOGGER.error("AdminController updateOrder method - Exit");
	    return "redirect:/admin/fetchAllJourneyFoodOrder";
	}
	
	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		LOGGER.info("AdminController deleteUser method - Enter =>id :"+id);
		try {
			userService.deleteUser(id);
		    LOGGER.info("AdminController deleteUser method - Exit successful");
	    }
		catch(IllegalArgumentException e) {
			LOGGER.error("AdminController deleteUser method - Exit"+ e.getMessage());
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
