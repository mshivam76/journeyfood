package org.brahmakumaris.journeyfood.service;

import java.util.List;
import java.util.Optional;

import org.brahmakumaris.journeyfood.controller.HomeController;
import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.CreateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.order.web.JourneyFoodOrderCreationParameters;
import org.brahmakumaris.journeyfood.repository.JourneyFoodOrderRepository;
import org.brahmakumaris.journeyfood.security.CustomUserDetails;
import org.brahmakumaris.journeyfood.security.UserService;
import org.brahmakumaris.journeyfood.security.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class JourneyFoodServiceImpl implements JourneyFoodService {
	private final JourneyFoodOrderRepository repository;
	private Logger LOGGER = LoggerFactory.getLogger(JourneyFoodServiceImpl.class);
	@Autowired
	private UserService defaultUserService;
	
    public JourneyFoodServiceImpl(JourneyFoodOrderRepository repository) {
        this.repository = repository;
    }
    
    public UserEntity getCurrentLoggedInUserData() {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserEntity user = null;
		  if(principal instanceof CustomUserDetails && principal!=null) {
				String username = ((CustomUserDetails)principal).getUsername();
				user = defaultUserService.getUser(username);
			  } 
		  else {
			String username = principal.toString();
			user = defaultUserService.getUser(username);
		  }
		  return user;
    }
    
    @Override
    public JourneyFoodOrder createJourneyFoodOrder(JourneyFoodOrderCreationParameters parameters) {
    	JourneyFoodOrder journeyFoodOrder = null;
    	UserEntity user = getCurrentLoggedInUserData();
    	if(user!=null) {
    		journeyFoodOrder= new JourneyFoodOrder(parameters.getHeadCount(), parameters.getDateOfOrderPlaced(), parameters.getDateOfDeparture(), 
    				parameters.getMealRetrievalTime(),	getCurrentLoggedInUserData(), parameters.getThepla(), parameters.getPuri(), 
    				parameters.getRoti(), parameters.getAchar(), parameters.getJam(), parameters.getBread(), parameters.getOthers());
    		return repository.save(journeyFoodOrder);
    	}
    	else
        return null;
    }

    @Override
    public List<JourneyFoodOrder> getOrders() {
        return (List<JourneyFoodOrder>) repository.findAll();
    }
	   
    @Override
	public JourneyFoodOrder findByOrderId(long id) {
		return repository.findById(id).get();
	}
    
	@Override
    public List<JourneyFoodOrder> getOrdersByUser(UserEntity user) {
        return (List<JourneyFoodOrder>) repository.findByUser(user);
    }

	@Override
	public void delete(long id) throws IllegalArgumentException{
		Optional<JourneyFoodOrder> order = repository.findById(id);
		if(order.isPresent())
			repository.deleteById(id);
	}

	@Override
	public void update(long id) throws IllegalArgumentException{
		Optional<JourneyFoodOrder> order = repository.findById(id);
		if(order.isPresent())
			repository.save(order.get());
	}

	@Override
	public void updateOrder(CreateJourneyFoodOrderFormData order) {
		LOGGER.info("JourneyFoodServiceImpl updateOrder method - Enter ");
		JourneyFoodOrder journeyFoodOrder= new JourneyFoodOrder(order.getId(), order.getHeadCount(), order.getDateOfOrderPlaced(), order.getDateOfDeparture(), 
				order.getMealRetrievalTime(),	getCurrentLoggedInUserData(), order.getThepla(), order.getPuri(), 
				order.getRoti(), order.getAchar(), order.getJam(), order.getBread(), order.getOthers());
		 LOGGER.info("JourneyFoodServiceImpl updateOrder method - Exit =>order(object/null): "+ repository.save(journeyFoodOrder));
	}
}