package org.brahmakumaris.journeyfood.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.UserSignUpFormData;
import org.brahmakumaris.journeyfood.security.DefaultUserService;
import org.brahmakumaris.journeyfood.security.exceptions.InvalidTokenException;
import org.brahmakumaris.journeyfood.security.exceptions.UserAlreadyExistException;
import org.brahmakumaris.journeyfood.security.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
/*
 * https://www.baeldung.com/spring-boot-crud-thymeleaf -->reference link
 * https://www.codejava.net/frameworks/spring-boot/user-registration-and-login-tutorial
 */
@Controller
public class AutheticationController {
	
	private Logger LOGGER = LoggerFactory.getLogger(AutheticationController.class);

	@Autowired
	private DefaultUserService userService;
	
	@Autowired
	MessageSource messageSource;
	
    @GetMapping("/register")
    public String register(final Model model) {
    	model.addAttribute("user", new UserSignUpFormData());
        return "register";
    }
    
    @PostMapping("/register")//Post operation
    public ModelAndView register(@Valid @ModelAttribute("user")UserSignUpFormData user, BindingResult result,HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView("register", "user", user);
    	LOGGER.info("AutheticationController register method - Entered");
    	if (result.hasErrors()) {
            return mav;
        }
    	else {
    		try {
                userService.register(user,DefaultUserService.getSiteURL(request));
            }catch (Exception e){
            	if(e instanceof UserAlreadyExistException)	mav.addObject("message", "An account with this email already exists. Please login.");
            	LOGGER.error(e.getMessage());
                return mav;
            }
    	  LOGGER.info("AutheticationController register method Exit: User => "+user);	
          return new ModelAndView("signup-success", "user", user);
    	}
    }
    
	@GetMapping("/verify")
	public String verify(@Param("token") String token, HttpServletRequest request) {
		LOGGER.info("AutheticationController verify method Enter: token => "+token);
    	try {
    		if (userService.verifyUser(token)) {
    			LOGGER.info("AutheticationController verify method Exit: token => "+token);
    			return "verify_success";
    		}
    		else {
    			LOGGER.info("AutheticationController verify method Exit: token => "+token);
    			return "verify_fail";
    		}
		} catch (InvalidTokenException e) {
			LOGGER.info("AutheticationController verify method Exit: token => "+token);
			return "verify_fail";
		}
	}
	
   @GetMapping("/login")//to fetch form
   public ModelAndView login( 
           @RequestParam(name="logout", required = false)String logout, HttpServletRequest request) {
	   ModelAndView mv = new ModelAndView("login");
       if(logout!=null) {
           mv.addObject("logout", "You have been successfully logged out!");
       }
       mv.addObject("title", "Journeyfood Login");
       return mv;
   }
   
   @GetMapping("/login-error")
   public String login(HttpServletRequest request, Model model) {
       HttpSession session = request.getSession(false);
       String errorMessage = null;
       if (session != null) {
           AuthenticationException ex = (AuthenticationException) session
                   .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
           if (ex != null) {
               errorMessage = ex.getMessage();
           }
       }
       model.addAttribute("errorMessage", errorMessage);
       return "login";
   }
    
   @GetMapping("/verifyUser")
   public String resetPassword(@Param("token")String token, HttpServletRequest req) {
	   LOGGER.info("AutheticationController - resetPassword method Enter: token => "+token);
	   try {
   		if (userService.verifyUser(token)) {
   			LOGGER.info("AutheticationController - resetPassword method User Verified successfully");
   			return "resetPassword";
   		}
   		else {
   			LOGGER.info("AutheticationController - resetPassword failed");
   			return "verify_fail";
   		}
		} catch (InvalidTokenException e) {
			LOGGER.error("AutheticationController - resetPassword method exception "+e.getMessage());
			return "verify_fail";
		}
   }
   
   @GetMapping("/forgotPassword")
   public String forgetPassword() {
	   return "forgotPassword";
   }
   
   @PostMapping("/forgotPassword")//Post operation
   public String forgotPassword(Model map,HttpServletRequest request) {
	   String email = request.getParameter("email");
	   LOGGER.info("AutheticationController forgotPassword POST method : email => "+ email);
	   String link;
	   try {
		  link = DefaultUserService.getSiteURL(request);
          userService.forgotPassword(email, link);
       }catch (Exception e){
       	if(e instanceof UserNotFoundException) {
       		map.addAttribute("errorUserMessage", e.getMessage());
       		LOGGER.error(e.getMessage());
       		return "forgotPassword";
       	}
       	else {
       		map.addAttribute("errorEmailMessage", e.getMessage());
       		LOGGER.error(e.getMessage());           		
       		return "forgotPassword";
       	}
       }
	   LOGGER.info("AutheticationController forgotPassword method end");
	   return "checkYouEmailResetPassword";
   }
   
   @GetMapping("/verifyUserResetPassword")
   public String verifyUserResetPassword(@Param("token") String token, Model model, HttpServletRequest request) {
	   LOGGER.info("AutheticationController verifyUserResetPassword method end");   
	   try {
			if (userService.verifyUserResetPassword(token)) {
				LOGGER.info("AutheticationController verifyUserResetPassword method end");
				model.addAttribute("token", token);
				return "resetPassword";
			}
			else {
				LOGGER.info("AutheticationController verifyUserResetPassword method FAILED");
				return "verify_fail";
			}
	   } 
	   catch (InvalidTokenException e) {
		   LOGGER.error(e.getMessage());
		   return "verify_fail";
	   }
	}
   
   @GetMapping("/resetPassword")
   public String resetPassword() {
   		return "forgotPassword";
   }
   
   @PostMapping("/resetPassword")//Post operation
   public String resetPassword(HttpServletRequest request, Model model) {
	   LOGGER.info("HomeController resetPassword method");
	   String token = request.getParameter("token");
	   String password = request.getParameter("password");
	   LOGGER.info("HomeController resetPassword POST method : token => "+ token);
	   UserEntity user = userService.getByResetPasswordToken(token);
	   model.addAttribute("title", "Reset your password");
	   if (user == null) {
	        model.addAttribute("message", "Invalid Token");
	        LOGGER.info("HomeController resetPassword method ");
	        return "message";
	   } else {           
	       userService.updatePassword(user, password);
	       model.addAttribute("message", "You have successfully changed your password.");
	   }
	   return "passwordChangedSuccessfully";
   }
   
   @ResponseStatus(HttpStatus.NOT_FOUND)
   @ExceptionHandler(UsernameNotFoundException.class)
   public ModelAndView userNameNotFound(Exception e) {
		LOGGER.error("AuthenticationController userNameNotFound Exception - Enter");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		mv.addObject("ex", e.getMessage());
		LOGGER.error(e.getMessage());
		return mv;
   }
}