package org.brahmakumaris.journeyfood.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.brahmakumaris.journeyfood.entity.AggregateJourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.CreateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.order.web.JourneyFoodOrderCreationParameters;
import org.brahmakumaris.journeyfood.order.web.UpdateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.repository.JourneyFoodOrderRepository;
import org.brahmakumaris.journeyfood.security.CustomUserDetails;
import org.brahmakumaris.journeyfood.security.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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
    		journeyFoodOrder= new JourneyFoodOrder(parameters.getHeadCount(), parameters.getDateOfOrderPlaced(), parameters.getDateOfDeparture(), parameters.getMealRetrievalDate(),
    				parameters.getMealRetrievalTime(),	getCurrentLoggedInUserData(), "PLACED", parameters.getThepla(), parameters.getPuri(), 
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
    public List<JourneyFoodOrder> getOrdersByUser() {
		UserEntity user = getCurrentLoggedInUserData();
        return (List<JourneyFoodOrder>) repository.findEnabledOrderByUserId(user.getUserId());
    }
	
	@Override
	public void delete(long id) throws IllegalArgumentException{
		Optional<JourneyFoodOrder> order = repository.findById(id);
		if(order.isPresent())
			repository.updateOrderStatus(id,"CANCELLED");
		else 
			throw new IllegalArgumentException("Unable to find order with id - "+id);
	}
	
	@Override
	public void orderCompleted(long id) throws IllegalArgumentException{
		Optional<JourneyFoodOrder> order = repository.findById(id);
		if(order.isPresent())
			repository.updateOrderStatus(id,"DELIVERED");
		else 
			throw new IllegalArgumentException("Unable to find order with id - "+id);
	}

	@Override
	public void update(long id) throws IllegalArgumentException{
		Optional<JourneyFoodOrder> order = repository.findById(id);
		if(order.isPresent())
			repository.save(order.get());
		else 
			throw new IllegalArgumentException("Unable to find order with id - "+id);
	}

	@Override
	public void updateOrderAdmin(CreateJourneyFoodOrderFormData order) {
		LOGGER.info("JourneyFoodServiceImpl updateOrder method - Enter ");
		JourneyFoodOrder journeyFoodOrder= repository.getOne(order.getOrderId());
				journeyFoodOrder.setHeadCount(order.getHeadCount());
				journeyFoodOrder.setDateOfDeparture(order.getDateOfDeparture());
				journeyFoodOrder.setMealRetrievalDate(order.getMealRetrievalDate());journeyFoodOrder.setMealRetrievalTime(order.getMealRetrievalTime());
				journeyFoodOrder.setThepla(order.getThepla());journeyFoodOrder.setPuri(order.getPuri());journeyFoodOrder.setRoti(order.getRoti()); 
				journeyFoodOrder.setAchar(order.getAchar()); journeyFoodOrder.setJam(order.getJam());journeyFoodOrder.setBread(order.getBread());
				journeyFoodOrder.setOthers(order.getOthers());
				if(order.getOrderStatus()!=null) journeyFoodOrder.setOrderStatus(order.getOrderStatus());
		 LOGGER.info("JourneyFoodServiceImpl updateOrder method - Exit =>order(object/null): "+ repository.save(journeyFoodOrder));
	}

	@Override
	public void updateOrder(UpdateJourneyFoodOrderFormData order) {
		LOGGER.info("JourneyFoodServiceImpl updateOrder method - Enter ");
		JourneyFoodOrder journeyFoodOrder= repository.getOne(order.getOrderId());
				journeyFoodOrder.setHeadCount(order.getHeadCount());
				journeyFoodOrder.setThepla(order.getThepla());journeyFoodOrder.setPuri(order.getPuri());journeyFoodOrder.setRoti(order.getRoti()); 
				journeyFoodOrder.setAchar(order.getAchar()); journeyFoodOrder.setJam(order.getJam());journeyFoodOrder.setBread(order.getBread());
				journeyFoodOrder.setOthers(order.getOthers());
				if(order.getOrderStatus()!=null) journeyFoodOrder.setOrderStatus(order.getOrderStatus());
		 LOGGER.info("JourneyFoodServiceImpl updateOrder method - Exit =>order(object/null): "+ repository.save(journeyFoodOrder));
	}
	
	@Override
	public List<JourneyFoodOrder> getOrdersNotDisabledData() {
		//Need to give UI to fetch order which are Delivered /Cancelled/Placed order status
		return (List<JourneyFoodOrder>) repository.findByOrderStatus("PLACED");
	}

	@Override
	public AggregateJourneyFoodOrder getOrdersByDateAndNotDisabled(LocalDate mealRetrievalDate) {
		return repository.getOrdersByDateAndNotDisabled("PLACED",mealRetrievalDate);
	}
	
	@Override
	public List<JourneyFoodOrder> getOrdersByDate(LocalDate mealRetrievalDate) {
		return repository.getOrdersByDate(mealRetrievalDate);
	}
	
	@Override
	public List<JourneyFoodOrder> getOrdersByDate(LocalDate mealRetrievalDate, String orderStatus) {
		return repository.getOrdersByDate(mealRetrievalDate, orderStatus);
	}

	@Override
	public List<JourneyFoodOrder> getOrdersByDateRangeAndOrderStatus(LocalDate fromDate, LocalDate endDate, String orderStatus) {
		return repository.getOrdersByDateRangeAndOrderStatus(fromDate, endDate, orderStatus);
	}
	
	@Override
	public List<JourneyFoodOrder> getOrdersByDateRange(LocalDate fromDate, LocalDate endDate) {
		return repository.getOrdersByDateRange(fromDate, endDate);
	}

}