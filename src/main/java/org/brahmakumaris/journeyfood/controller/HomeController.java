package org.brahmakumaris.journeyfood.controller;

import java.util.List;

import javax.validation.Valid;

import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.CreateJourneyFoodOrderFormData;
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
import org.springframework.web.servlet.ModelAndView;
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
    public String addJourneyFoodOrder(@Valid @ModelAttribute("createJourneyFoodOrderFormData")CreateJourneyFoodOrderFormData formData, BindingResult result, Model model) {
		LOGGER.info("HomeController addJourneyFoodOrder method - Entered");
		if (result.hasErrors()) {
			LOGGER.error("HomeController addJourneyFoodOrder method - Error occured");
            return "add-journeyFoodOrder";
        }
        model.addAttribute("journeyFoorOrder", formData);
        journeyFoodServiceImpl.createJourneyFoodOrder(formData.toParams());
        LOGGER.info("HomeController addJourneyFoodOrder method - Exit");
        return "redirect:/fetchAllJourneyFoodOrdersNotDisabled";
    }
   
	@GetMapping("/fetchAllJourneyFoodOrdersNotDisabled")
    public ModelAndView fetchAllJourneyFoodOrdersNotDisabled() {
		LOGGER.info("AdminController fetchAllJourneyFoodOrdersNotDisabled method - Enter");
	 	List<JourneyFoodOrder> orders=journeyFoodServiceImpl.getOrdersByUser();
	 	LOGGER.info("AdminController fetchAllJourneyFoodOrdersNotDisabled method - Exit =>orders: "+orders);
        return new ModelAndView("fetchJourneyFoodOrdersByLoggedInUser", "orders", orders.isEmpty()?null:orders);
    }
	
	@GetMapping("/delete/{id}")
	public String deleteOrder(@PathVariable("id") long id, Model model) {
		LOGGER.info("HomeController deleteOrder method - Enter =>id :"+id);
		try {
			journeyFoodServiceImpl.delete(id);
		    LOGGER.info("HomeController deleteOrder method - Exit successful");
	    }
		catch(IllegalArgumentException e) {
			LOGGER.error("HomeController deleteOrder method - Exit"+ e.getMessage());
			return "redirect:/error";
		}
	    return "redirect:/fetchAllJourneyFoodOrdersNotDisabled";
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
	public String updateOrder( @Valid @ModelAttribute("order") CreateJourneyFoodOrderFormData order, BindingResult result, @PathVariable("id") long id) {
	    if (result.hasErrors()) {
	    	LOGGER.error("HomeController updateOrder method - Error occured");
            return "update-journeyFoodOrder";
	    }
	    journeyFoodServiceImpl.updateOrder(order);
	    LOGGER.error("HomeController updateOrder method - Exit");
	    return "redirect:/fetchAllJourneyFoodOrdersNotDisabled";
	}
}