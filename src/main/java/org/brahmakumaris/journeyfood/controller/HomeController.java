package org.brahmakumaris.journeyfood.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.CreateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.order.web.UserSignUpFormData;
import org.brahmakumaris.journeyfood.security.UserService;
import org.brahmakumaris.journeyfood.security.exceptions.UserAlreadyExistException;
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
import org.springframework.web.servlet.ModelAndView;
/*
 * https://www.baeldung.com/spring-boot-crud-thymeleaf -->reference link
 * https://www.codejava.net/frameworks/spring-boot/user-registration-and-login-tutorial
 */
@Controller
public class HomeController {
	private Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private JourneyFoodService journeyFoodServiceImpl;
	
	@Autowired
	private UserService userService;
	
    @GetMapping("/register")
    public String register(final Model model) {
    	logger.trace("HomeController register method - Entered");
    	model.addAttribute("user", new UserSignUpFormData());
    	logger.trace("HomeController register method - Exit");
        return "register";
    }
    
    @PostMapping("/register")//Post operation
    public ModelAndView register(@Valid @ModelAttribute("user")UserSignUpFormData user, BindingResult result,HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView("register", "user", user);
    	if (result.hasErrors()) {
            return mav;
        }
    	else {
    		try {
                userService.register(user);
            }catch (UserAlreadyExistException e){
                mav.addObject("message", "An account with this email already exists.");
                return mav;
            }
           return new ModelAndView("signup-success", "user", user);
    	}
    }
    
    @GetMapping("/addJourneyFoodOrder")
    public String addJourneyFoodOrder(CreateJourneyFoodOrderFormData formData) {
        return "add-journeyFoodOrder";
    }
    
	@PostMapping("/addJourneyFoodOrder")
    public String addJourneyFoodOrder(@Valid @ModelAttribute("createJourneyFoodOrderFormData")CreateJourneyFoodOrderFormData formData, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-journeyFoodOrder";
        }
        model.addAttribute("journeyFoorOrder", formData);
        journeyFoodServiceImpl.createJourneyFoodOrder(formData.toParams());
        return "redirect:/index";
    }

	
   @GetMapping("/login")//to fetch form
	public String login(Model model) {
	    model.addAttribute("user", new UserEntity());
	    return "login";
	}
    
}
