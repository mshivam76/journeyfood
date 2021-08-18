package org.brahmakumaris.journeyfood.controller;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.PostMapping;
/*
 * https://www.baeldung.com/spring-boot-crud-thymeleaf -->reference link
 * https://www.codejava.net/frameworks/spring-boot/user-registration-and-login-tutorial
 */
@Controller
public class HomeController {
	private Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private JourneyFoodService journeyFoodServiceImpl;
	
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
        return "redirect:/index";
    }
   
}
