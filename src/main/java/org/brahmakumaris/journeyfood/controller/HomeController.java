package org.brahmakumaris.journeyfood.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.order.web.CreateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.order.web.UpdateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.service.JourneyFoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/*
 * https://www.baeldung.com/spring-boot-crud-thymeleaf -->reference link
 * https://www.codejava.net/frameworks/spring-boot/user-registration-and-login-tutorial
 */
@Controller
public class HomeController {
	private Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private JourneyFoodService journeyFoodServiceImpl;
	
    @GetMapping("/error")
    public String error() {
        return "error";
    }
	
    @GetMapping("/addJourneyFoodOrder")
    public String addJourneyFoodOrder(CreateJourneyFoodOrderFormData formData) {
        return "add-journeyFoodOrder";
    }
    
	@PostMapping("/addJourneyFoodOrder")
    public String addJourneyFoodOrder(@Valid @ModelAttribute("createJourneyFoodOrderFormData")CreateJourneyFoodOrderFormData formData, BindingResult result, 
    		RedirectAttributes redirectAttributes,  Model model) throws UnsupportedEncodingException, MessagingException {
//		redirectAttributes.addFlashAttribute("message", "Failed");
//	    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		LOGGER.info("HomeController addJourneyFoodOrder method - Entered");
		if (result.hasErrors()) {
			LOGGER.error("HomeController addJourneyFoodOrder method - Error occured");
            return "add-journeyFoodOrder";
        }
        model.addAttribute("journeyFoorOrder", formData);
        journeyFoodServiceImpl.createJourneyFoodOrder(formData.toParams());
        LOGGER.info("HomeController addJourneyFoodOrder method - Exit");
        redirectAttributes.addFlashAttribute("message", "Order added successfully");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/fetchAllJourneyFoodOrdersByLoggedInUser";
    }
   
	@GetMapping("/fetchAllJourneyFoodOrdersByLoggedInUser")
//    public ModelAndView fetchAllJourneyFoodOrdersByLoggedInUser(Model model) {
	public String fetchAllJourneyFoodOrdersByLoggedInUser(Model model) {
		LOGGER.info("AdminController fetchAllJourneyFoodOrdersByLoggedInUser method - Enter");
	 	List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrdersByUser();
	 	LOGGER.info("AdminController fetchAllJourneyFoodOrdersByLoggedInUser method - Exit =>orders: "+orders);
//	 	return new ModelAndView("fetchJourneyFoodOrdersByLoggedInUser", "orders", orders.isEmpty()?null:orders);
		return paginateAllOrder(1, model);
		
    }
	
	@GetMapping("/fetchAllOrderByLoggedInUser/{pageNo}")
    public String paginateAllOrder(@PathVariable(value="pageNo") Integer pageNo, Model model) {
		int pageSize=8;
		LOGGER.info("HomeController paginateAllOrder method - Enter");
//	 	List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrders();
	 	Page<JourneyFoodOrder> page= journeyFoodServiceImpl.getPaginatedLoggedInUserOrders(pageNo, pageSize);
	 	List<JourneyFoodOrder> orders = page.getContent();
	 	model.addAttribute("title", "Show All Orders");
//	 	model.addAttribute("orders", orders.isEmpty()?null:orders);
	 	model.addAttribute("currentPage", pageNo);
	 	model.addAttribute("totalPages", page.getTotalPages());
	 	model.addAttribute("orders", page.isEmpty()?null:page);
	 	model.addAttribute("url","/fetchAllOrderByLoggedInUser/");
//	 	model.addAttribute("totalItems", page.getTotalElements());
	 	LOGGER.info("HomeController paginateAllOrder method - Exit =>orders: "+orders);
        return "fetchJourneyFoodOrdersByLoggedInUser";
    }
	
	@GetMapping("/fetchPlacedOrdersByLoggedInUser")
    public String fetchPlacedOrdersByLoggedInUser(Model model) {
//		LOGGER.info("AdminController fetchPlacedOrdersByLoggedInUser method - Enter");
//	 	List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getPlacedOrdersByUser();
//	 	LOGGER.info("AdminController fetchPlacedOrdersByLoggedInUser method - Exit =>orders: "+orders);
//        return new ModelAndView("fetchPlacedOrdersByLoggedInUser", "orders", orders.isEmpty()?null:orders);
		return paginatePlacedOrdersByLoggedInUser(1, model);
    }
	
	@GetMapping("/fetchPlacedOrdersByLoggedInUser/{pageNo}")
    public String paginatePlacedOrdersByLoggedInUser(@PathVariable(value="pageNo") Integer pageNo, Model model) {
		int pageSize=8;
		LOGGER.info("HomeController paginatePlacedOrdersByLoggedInUser method - Enter");
	 	Page<JourneyFoodOrder> page=journeyFoodServiceImpl.getPaginatedPlacedOrdersByUser(pageNo, pageSize);
	 	model.addAttribute("title", "Show All Orders");
	 	model.addAttribute("currentPage", pageNo);
	 	model.addAttribute("totalPages", page.getTotalPages());
	 	model.addAttribute("orders", page.isEmpty()?null:page);
	 	model.addAttribute("url","/fetchPlacedOrdersByLoggedInUser/");
	 	LOGGER.info("HomeController paginatePlacedOrdersByLoggedInUser method - Exit =>orders: "+page);
        return "fetchPlacedOrdersByLoggedInUser";
    }
	

	
	@GetMapping("/delete/{id}")
	public String deleteOrder(@PathVariable("id") long id, Model model) throws UnsupportedEncodingException, MessagingException {
		LOGGER.info("HomeController deleteOrder method - Enter =>id :"+id);
		try {
			journeyFoodServiceImpl.delete(id);//Updating order to cancelled status
		    LOGGER.info("HomeController deleteOrder method - Exit successful");
	    }
		catch(IllegalArgumentException e) {
			LOGGER.error("HomeController deleteOrder method - Exit"+ e.getMessage());
			return "redirect:/error";
		}
	    return "redirect:/fetchAllJourneyFoodOrdersByLoggedInUser";
	}
	
	@GetMapping("/edit/{id}")
	public String updateOrder(@PathVariable("id") long id, Model model) {
		LOGGER.info("HomeController updateOrder method - Enter =>id :"+id);
	    try {
	    	JourneyFoodOrder order = journeyFoodServiceImpl.findByOrderId(id);
	    	model.addAttribute("order", order);
		    LOGGER.info("HomeController updateOrder method - Exit =>order(object/null): "+ id);
	    }
		catch(IllegalArgumentException e) {
			LOGGER.info("HomeController updateOrder method - Exit =>orders: "+id);
			return "redirect:/error";
		}
	    return "update-journeyFoodOrder";
	}
	
	@PostMapping("/update/{id}")
	public String updateOrder( @Valid @ModelAttribute("order") UpdateJourneyFoodOrderFormData order, BindingResult result, RedirectAttributes redirectAttributes, @PathVariable("id") long id) {
	    if (result.hasErrors()) {
	    	LOGGER.error("HomeController updateOrder method - Error occured");
            return "update-journeyFoodOrder";
	    }
	    journeyFoodServiceImpl.updateOrder(order);
	    redirectAttributes.addFlashAttribute("message", "Order "+order.getOrderId()+" updated successfully");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
	    LOGGER.error("HomeController updateOrder method - Exit");
	    return "redirect:/fetchAllJourneyFoodOrdersByLoggedInUser";
	}
}