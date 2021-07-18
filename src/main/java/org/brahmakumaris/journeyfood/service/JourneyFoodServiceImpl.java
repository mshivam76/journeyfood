package org.brahmakumaris.journeyfood.service;

import java.util.List;

import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.order.web.JourneyFoodOrderCreationParameters;
import org.brahmakumaris.journeyfood.repository.JourneyFoodOrderRepository;
import org.springframework.stereotype.Service;


@Service
public class JourneyFoodServiceImpl implements JourneyFoodService {
	 private final JourneyFoodOrderRepository repository;

	    public JourneyFoodServiceImpl(JourneyFoodOrderRepository repository) {
	        this.repository = repository;
	    }

	    @Override
	    public JourneyFoodOrder createJourneyFoodOrder(JourneyFoodOrderCreationParameters parameters) {
	    	JourneyFoodOrder journeyFoodOrder = new JourneyFoodOrder(parameters.getNameOfCenter(), parameters.getContactNoOfGuide(), parameters.getHeadCount(), parameters.getContactNoOfGuide(), parameters.getDateOfDeparture(), 
	    			parameters.getMealRetrievalTime(), parameters.getThepla(), parameters.getPuri(), parameters.getRoti(), parameters.getAchar(), parameters.getJam(), parameters.getBread(), parameters.getOthers());
	        return repository.save(journeyFoodOrder);
	    }

	    @Override
	    public List<JourneyFoodOrder> getOrders() {
	        return (List<JourneyFoodOrder>) repository.findAll();
	    }
}
