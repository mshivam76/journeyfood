package org.brahmakumaris.journeyfood.repository;

import java.util.List;

import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyFoodOrderRepository extends JpaRepository<JourneyFoodOrder, Long>{
	
	@Override
	void delete(JourneyFoodOrder order);

	List<JourneyFoodOrder> findByUser(UserEntity user);
	
	@Modifying
	@Query("update JourneyFoodOrder j set j.isRemoved =true where j.id =:id")
	void updateIsRemoved(Long id);

	List<JourneyFoodOrder> findByIsRemoved(boolean isRemoved);
}
