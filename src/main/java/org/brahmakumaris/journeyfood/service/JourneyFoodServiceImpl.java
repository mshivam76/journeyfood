package org.brahmakumaris.journeyfood.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.brahmakumaris.journeyfood.entity.AggregateJourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.CreateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.order.web.JourneyFoodOrderCreationParameters;
import org.brahmakumaris.journeyfood.order.web.UpdateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.repository.JourneyFoodOrderRepository;
import org.brahmakumaris.journeyfood.security.CustomUserDetails;
import org.brahmakumaris.journeyfood.security.UserService;
import org.brahmakumaris.journeyfood.security.exceptions.OrderNotFoundException;
import org.brahmakumaris.journeyfood.utils.EmailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	
    @Autowired
    EmailUtils emailUtils;
	
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
    public JourneyFoodOrder createJourneyFoodOrder(JourneyFoodOrderCreationParameters parameters) throws UnsupportedEncodingException, MessagingException {
    	JourneyFoodOrder journeyFoodOrder = null;
    	UserEntity user = getCurrentLoggedInUserData();
    	if(user!=null) {
    		journeyFoodOrder= new JourneyFoodOrder(parameters.getHeadCount(), parameters.getDateOfOrderPlaced(), parameters.getDateOfDeparture(), parameters.getMealRetrievalDate(),
    				parameters.getMealRetrievalTime(),	getCurrentLoggedInUserData(), "PLACED", parameters.getThepla(), parameters.getPuri(), 
    				parameters.getRoti(), parameters.getAchar(), parameters.getJam(), parameters.getBread(), parameters.getItems());
    		emailUtils.orderPlacedMail(journeyFoodOrder.getUser());
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
	public JourneyFoodOrder findByOrderId(long id)  {
    	if(repository.findById(id).isEmpty())
    		throw new OrderNotFoundException("Order does not exists with id: "+id);
    	else return repository.findById(id).get();
	}
    
	@Override
    public List<JourneyFoodOrder> getOrdersByUser()  {
		UserEntity user = getCurrentLoggedInUserData();
		List<JourneyFoodOrder> orders = repository.findAllOrderByUserId(user.getUserId());
		if(orders.isEmpty())throw new OrderNotFoundException("No Order is placed yet. Please add(place) orders to view orders");
		else return orders;
    }
	
	@Override
    public List<JourneyFoodOrder> getPlacedOrdersByUser()  {
		UserEntity user = getCurrentLoggedInUserData();
		List<JourneyFoodOrder> orders = repository.findPlacedOrderByUserId(user.getUserId());
		return orders;
    }
	
	@Override
    public List<JourneyFoodOrder> getAllPlacedOrders()  {
		List<JourneyFoodOrder> orders = repository.findByOrderStatus("PLACED");
		return orders;
    }
	
	@Override
    public Page<JourneyFoodOrder> getPaginatedPlacedOrdersByUser(int pageNo, int pageSize)  {
		UserEntity user = getCurrentLoggedInUserData();
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		Page<JourneyFoodOrder> orders = repository.findPlacedOrderByUserId(user.getUserId(), pageable);
		return orders;
    }
	
	@Override
	public void delete(long id) throws UnsupportedEncodingException, MessagingException {
		Optional<JourneyFoodOrder> order = repository.findById(id);
		if(order.isPresent()) {
			repository.updateOrderStatus(id,"CANCELLED");
			emailUtils.orderCancelledMail(order.get().getUser(), order.get().getOrderId());
		}
		else 
			throw new OrderNotFoundException("Unable to find order with id - "+id);
	}
	
	@Override
	public void orderCompleted(long id) throws UnsupportedEncodingException, MessagingException {
		Optional<JourneyFoodOrder> order = repository.findById(id);
		if(order.isPresent()) {
			repository.updateOrderStatus(id,"DELIVERED");
			emailUtils.orderDeliveredMail(order.get().getUser(), order.get().getOrderId());
		}
		else 
			throw new OrderNotFoundException("Unable to find order with id - "+id);
	}

	@Override
	public void update(long id) {
		Optional<JourneyFoodOrder> order = repository.findById(id);
		if(order.isPresent())
			repository.save(order.get());
		else 
			throw new OrderNotFoundException("Unable to find order with id - "+id);
	}

	@Override
	public void updateOrderAdmin(CreateJourneyFoodOrderFormData order) throws UnsupportedEncodingException, MessagingException{
		LOGGER.info("JourneyFoodServiceImpl updateOrder method - Enter ");
		JourneyFoodOrder journeyFoodOrder= repository.getOne(order.getOrderId());
		journeyFoodOrder.setHeadCount(order.getHeadCount());
		journeyFoodOrder.setDateOfDeparture(order.getDateOfDeparture());
		journeyFoodOrder.setMealRetrievalDate(order.getMealRetrievalDate());journeyFoodOrder.setMealRetrievalTime(order.getMealRetrievalTime());
		journeyFoodOrder.setThepla(order.getThepla());journeyFoodOrder.setPuri(order.getPuri());journeyFoodOrder.setRoti(order.getRoti()); 
		journeyFoodOrder.setAchar(order.getAchar()); journeyFoodOrder.setJam(order.getJam());journeyFoodOrder.setBread(order.getBread());
		journeyFoodOrder.setItems(order.getItems());
		if(order.getOrderStatus()!=null) {
			journeyFoodOrder.setOrderStatus(order.getOrderStatus());
			emailUtils.orderUpdatedMail(journeyFoodOrder.getUser(), order.getOrderId());
		}
					
		 LOGGER.info("JourneyFoodServiceImpl updateOrder method - Exit =>order(object/null): "+ repository.save(journeyFoodOrder));
	}

	@Override
	public void updateOrder(UpdateJourneyFoodOrderFormData order) {
		LOGGER.info("JourneyFoodServiceImpl updateOrder method - Enter ");
		JourneyFoodOrder journeyFoodOrder= repository.getOne(order.getOrderId());
				journeyFoodOrder.setHeadCount(order.getHeadCount());
				journeyFoodOrder.setThepla(order.getThepla());journeyFoodOrder.setPuri(order.getPuri());journeyFoodOrder.setRoti(order.getRoti()); 
				journeyFoodOrder.setAchar(order.getAchar()); journeyFoodOrder.setJam(order.getJam());journeyFoodOrder.setBread(order.getBread());
				journeyFoodOrder.setItems(order.getItems());
				if(order.getOrderStatus()!=null) journeyFoodOrder.setOrderStatus(order.getOrderStatus());
		 LOGGER.info("JourneyFoodServiceImpl updateOrder method - Exit =>order(object/null): "+ repository.save(journeyFoodOrder));
	}
	
	@Override
	public List<JourneyFoodOrder> getOrdersNotDisabledData()  {
		//Need to give UI to fetch order which are Delivered /Cancelled/Placed order status
		List<JourneyFoodOrder> orders = repository.findByOrderStatus("PLACED");
		if(orders.isEmpty())throw new OrderNotFoundException("No Order PLACED  yet. Please add(place) orders to view orders");
		else return orders;
	}

	@Override
	public AggregateJourneyFoodOrder getOrdersByDateAndNotDisabled(LocalDate mealRetrievalDate) {
		return repository.getOrdersByDateAndNotDisabled("PLACED",mealRetrievalDate);
	}
	
	@Override
	public List<JourneyFoodOrder> getOrdersByDate(LocalDate mealRetrievalDate)  {
		List<JourneyFoodOrder> orders =repository.getOrdersByDate(mealRetrievalDate);
		if(orders.isEmpty())throw new OrderNotFoundException("No Orders yet for Date :"+mealRetrievalDate.toString());
		else return orders;
	}
	
	@Override
	public List<JourneyFoodOrder> getOrdersByDate(LocalDate mealRetrievalDate, String orderStatus)  {
		List<JourneyFoodOrder> orders =  (List<JourneyFoodOrder>)repository.getOrdersByDate(mealRetrievalDate, orderStatus);
		if(orders.isEmpty())throw new OrderNotFoundException("No Orders yet for Date :"+mealRetrievalDate.toString());
		else return orders;
	}

	@Override
	public List<JourneyFoodOrder> getOrdersByDateRangeAndOrderStatus(LocalDate fromDate, LocalDate endDate, String orderStatus)  {
		List<JourneyFoodOrder> orders = repository.getOrdersByDateRangeAndOrderStatus(fromDate, endDate, orderStatus);
		if(orders.isEmpty())throw new OrderNotFoundException("No Orders yet for Daterange : "+fromDate.toString()+" to "+endDate.toString());
		else return orders;
	}
	
	@Override
	public List<JourneyFoodOrder> getOrdersByDateRange(LocalDate fromDate, LocalDate endDate)  {
		List<JourneyFoodOrder> orders = repository.getOrdersByDateRange(fromDate, endDate);
		if(orders.isEmpty())throw new OrderNotFoundException("No Orders yet for Daterange : "+fromDate.toString()+" to "+endDate.toString());
		else return orders;
	}

	@Override
	public List<JourneyFoodOrder> getOrdersByDateAndSlot(LocalDate mealRetrievalDate, String mealRetrievalTime, String orderStatus) {
		List<JourneyFoodOrder> orders = repository.findByMealRetrievalDateAndMealRetrievalTimeAndOrderStatus(mealRetrievalDate, mealRetrievalTime);
		if(orders.isEmpty())throw new OrderNotFoundException("No Orders yet for Date : "+mealRetrievalDate.toString()+" and Pickup slot of - "+mealRetrievalTime+" Order Status is - "+orderStatus);
		else return orders;
	}
	
	@Override
	public Page<JourneyFoodOrder> findPaginated(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return this.repository.findAll(pageable);
	}
	
	@Override
	public Page<JourneyFoodOrder> getPaginatedLoggedInUserOrders(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return this.repository.findAllOrderByUserIdPaging(getCurrentLoggedInUserData().getUserId(), pageable);
	}
	
	@Override
	public Page<JourneyFoodOrder> getPaginatedOrdersByStatus(String orderStatus, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return this.repository.findByOrderStatus(orderStatus, pageable);
	}
	
	@Override
	public Page<JourneyFoodOrder> getPaginatedOrdersByStatus(long userId, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return this.repository.findPlacedOrderByUserId(userId, pageable);
	}
	
	@Override
	public Page<JourneyFoodOrder> getPaginatedLoggedInUserOrders(LocalDate date, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return this.repository.getOrdersByDate(date, pageable);
	}
	
	@Override
	public Page<JourneyFoodOrder> getPaginatedOrdersByStatus(LocalDate date, String orderStatus, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return this.repository.getOrdersByDate(date, orderStatus, pageable);
	}
	
	@Override
	public Page<JourneyFoodOrder> getOrdersByDateRangeAndOrderStatus(LocalDate fromDate, LocalDate endDate, String orderStatus, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return this.repository.getOrdersByDateRangeAndOrderStatus(fromDate, endDate, orderStatus, pageable);
	}
	
	@Override
	public Page<JourneyFoodOrder> getOrdersByDateRange(LocalDate fromDate, LocalDate endDate, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return this.repository.getOrdersByDateRange(fromDate, endDate, pageable);
	}

}