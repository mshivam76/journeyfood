package org.brahmakumaris.journeyfood.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.CreateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.order.web.UserSignUpFormData;
import org.brahmakumaris.journeyfood.security.UserService;
import org.brahmakumaris.journeyfood.security.exceptions.InvalidTokenException;
import org.brahmakumaris.journeyfood.security.exceptions.UserAlreadyExistException;
import org.brahmakumaris.journeyfood.service.JourneyFoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/*
 * https://www.baeldung.com/spring-boot-crud-thymeleaf -->reference link
 * https://www.codejava.net/frameworks/spring-boot/user-registration-and-login-tutorial
 */
@Controller
public class HomeController {
	private Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	 private static final String REDIRECT_LOGIN= "redirect:/login";
	@Autowired
	private JourneyFoodService journeyFoodServiceImpl;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	MessageSource messageSource;
	
    @GetMapping("/register")
    public String register(final Model model) {
    	LOGGER.trace("HomeController register method - Entered");
    	model.addAttribute("user", new UserSignUpFormData());
    	LOGGER.trace("HomeController register method - Exit");
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
            }catch (Exception e){
            	if(e instanceof UserAlreadyExistException)	mav.addObject("message", "An account with this email already exists.");
            	LOGGER.error(e.getMessage());
                return mav;
            }
           return new ModelAndView("signup-success", "user", user);
    	}
    }
    
	/*
	 * @GetMapping("/verify") public String
	 * registrationVerification(@RequestParam(required = false) String token, final
	 * Model model, RedirectAttributes redirAttr){ if(StringUtils.isEmpty(token)){
	 * redirAttr.addFlashAttribute("tokenError",
	 * messageSource.getMessage("user.registration.verification.missing.token",
	 * null,LocaleContextHolder.getLocale())); return REDIRECT_LOGIN; } try {
	 * userService.verifyUser(token); } catch (InvalidTokenException e) {
	 * redirAttr.addFlashAttribute("tokenError",
	 * messageSource.getMessage("user.registration.verification.invalid.token",
	 * null,LocaleContextHolder.getLocale())); return REDIRECT_LOGIN; }
	 * redirAttr.addFlashAttribute("verifiedAccountMsg",
	 * messageSource.getMessage("user.registration.verification.success",
	 * null,LocaleContextHolder.getLocale())); return REDIRECT_LOGIN; }
	 */
	@GetMapping("/verify")
	public ModelAndView verify(@Param("token") String token, HttpServletRequest request) {
    	try {
    		if (userService.verifyUser(token))
    			return new ModelAndView("verify_success", "user", "Verification Succeeded!");
    		else
    			return new ModelAndView("verify_fail", "user", "Verification Failed!");
		} catch (InvalidTokenException e) {
			return new ModelAndView("verify_fail", "user", "Verification Failed!");
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
    
//   @GetMapping("/forgetPassword")
//   public String forgetPassword(final Model model) {
//   	LOGGER.trace("HomeController forgetPassword method - Entered");
//   	model.addAttribute("user", new UserSignUpFormData());
//   	LOGGER.trace("HomeController forgetPassword method - Exit");
//    return "forgetPassword";
//   }
//   
//   @PostMapping("/forgetPassword")//Post operation
//   public String forgetPassword(@Valid @ModelAttribute("email")String email, BindingResult result,HttpServletRequest request) {
//	   if(StringUtils.isEmpty(email))
//		   
//	   return "login";
//   }
}
