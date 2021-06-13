package org.brahmakumaris.journeyfood.repository;

import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyFoodOrderRepository extends CrudRepository<JourneyFoodOrder, Long>{

}
