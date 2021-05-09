package org.brahmakumaris.journeyfood.controller;

import javax.validation.Valid;

import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.repository.JourneyFoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
/*
 * https://www.baeldung.com/spring-boot-crud-thymeleaf -->reference link
 */
@Controller
public class JourneyFoodOrderController {
	
	private final JourneyFoodOrderRepository journeyFoodOrderRepository;

    @Autowired
    public JourneyFoodOrderController(JourneyFoodOrderRepository journeyFoodOrderRepository) {
		// TODO Auto-generated constructor stub
    	this.journeyFoodOrderRepository = journeyFoodOrderRepository;
	}
    
    @GetMapping("/signup")
    public String showSignUpForm(JourneyFoodOrderRepository journeyFoodOrderRepository) {
        return "add-journeyFoodOrder";
    }
	
	@PostMapping("/addJourneyFoodOrder")
    public String addUser(@Valid JourneyFoodOrder journeyFoodOrder, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-journeyFoodOrder";
        }
        journeyFoodOrderRepository.save(journeyFoodOrder);
        return "redirect:/index";
    }
	
	@GetMapping("/index")
	public String showJourneyFoodOrderList(Model model) {
	    model.addAttribute("journeyFoodOrders", journeyFoodOrderRepository.findAll());
	    return "index";
	}
	
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		JourneyFoodOrder journeyFoodOrder = journeyFoodOrderRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid journeyFoodOrder Id:" + id));
	    
	    model.addAttribute("journeyFoodOrder", journeyFoodOrder);
	    return "update-journeyFoodOrder";
	}
	
	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") long id, @Valid JourneyFoodOrder journeyFoodOrder, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        journeyFoodOrder.setId(id);
	        return "update-journeyFoodOrder";
	    }
	        
	    journeyFoodOrderRepository.save(journeyFoodOrder);
	    return "redirect:/index";
	}
	    
	@GetMapping("/delete/{id}")
	public String deletejourneyFoodOrder(@PathVariable("id") long id, Model model) {
	    JourneyFoodOrder journeyFoodOrder = journeyFoodOrderRepository.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid journeyFoodOrder Id:" + id));
	    journeyFoodOrderRepository.delete(journeyFoodOrder);
	    return "redirect:/index";
	}
}
