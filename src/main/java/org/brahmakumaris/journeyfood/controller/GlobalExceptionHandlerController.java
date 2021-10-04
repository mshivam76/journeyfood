package org.brahmakumaris.journeyfood.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandlerController {
	private static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerController.class);
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception e) {
    	LOGGER.info("GlobalExceptionHandlerController - exception()  : starts");
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("exceptionError");
		mv.addObject("ex", e.getMessage());
		LOGGER.info("GlobalExceptionHandlerController - exception()  : end");
		return mv;
    }
    
    @ExceptionHandler(value = MessagingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView messageException(MessagingException e) {
    	LOGGER.info("GlobalExceptionHandlerController - messageException()  : starts");
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("emailSendingError");
		mv.addObject("ex", e.getMessage());
		LOGGER.info("GlobalExceptionHandlerController - messageException()  : end");
		return mv;
    }
    
    @ExceptionHandler(value = UnsupportedEncodingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView unsupportedEncodingException(UnsupportedEncodingException e) {
    	LOGGER.info("GlobalExceptionHandlerController - unsupportedEncodingException()  : starts");
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("emailSendingError");
		mv.addObject("ex", e.getMessage());
		LOGGER.info("GlobalExceptionHandlerController - unsupportedEncodingException()  : end");
		return mv;
    }
}
