package org.brahmakumaris.journeyfood.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

public class OrderForJourney {

	/*
	 *
	 * OrderNo	
	Date of Journey-
	Name of Bro/Sis
	Name of Center
	Guide/Teacher name
	Mobileno of Bro/Sis
	Contactno of  Guide/Teacher
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderNo;
    
    @NotBlank(message = "Name is mandatory")
    private String nameOfBk;
    
    @NotBlank(message = "Name is mandatory")
    private String nameOfCenter;
    
    @NotBlank(message = "Name is mandatory")
    private String nameOfGuide;
    
    @NotBlank(message = "Name is mandatory")
    private String contactNoOfBk;
    
    @NotBlank(message = "Name is mandatory")
    private String contactNoOfGuide;
    
    @NotBlank(message = "Name is mandatory")
    private Date dateOfJourney;
    
    
    @NotBlank(message = "Email is mandatory")
    private String email;
}
