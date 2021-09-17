package org.brahmakumaris.journeyfood.repository;

import java.util.List;

import org.brahmakumaris.journeyfood.entity.AggregateJourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface JourneyFoodOrderRepository extends JpaRepository<JourneyFoodOrder, Long>{
	
	@Override
	void delete(JourneyFoodOrder order);

	@Modifying
	@Query("update JourneyFoodOrder j set j.orderStatus =:orderStatus where j.orderId =:orderId")
	void updateOrderStatus(@Param("orderId")long orderId, @Param("orderStatus")String orderStatus);

	List<JourneyFoodOrder> findByOrderStatus(String  orderStatus);
//	@Modifying
	@Query("SELECT new JourneyFoodOrder(j.orderId, j.headCount,j.dateOfOrderPlaced,j.dateOfDeparture,j.mealRetrievalDate,j.mealRetrievalTime,j.orderStatus,j.thepla,"
			+ "j.puri,j.roti,j.achar,j.jam,j.bread,j.others) from JourneyFoodOrder j WHERE j.user.userId=:userId AND j.orderStatus='PLACED'")
	List<JourneyFoodOrder> findEnabledOrderByUserId(@Param("userId") long userId);

//	@Modifying
	@Query("SELECT new AggregateJourneyFoodOrder(SUM(headCount), SUM(bread), SUM(achar), SUM(jam) ,SUM(others), SUM(puri), SUM(roti), SUM(thepla), mealRetrievalDate)"
			+ "  FROM JourneyFoodOrder j WHERE j.orderStatus=:orderStatus AND j.mealRetrievalDate = :mealRetrievalDate")
	AggregateJourneyFoodOrder getOrdersByDateAndNotDisabled(@Param("orderStatus")String orderStatus, @Param("mealRetrievalDate")LocalDate mealRetrievalDate);
//	List<AggregateJourneyFoodOrder> getOrdersByDateAndNotDisabled(@Param("mealRetrievalDate")Date mealRetrievalDate);
}
