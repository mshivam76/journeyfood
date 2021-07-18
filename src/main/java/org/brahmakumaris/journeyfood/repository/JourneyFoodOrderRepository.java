package org.brahmakumaris.journeyfood.repository;

import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyFoodOrderRepository extends JpaRepository<JourneyFoodOrder, Long>{

}
