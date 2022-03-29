package org.brahmakumaris.journeyfood.repository;

import java.time.LocalDate;
import java.util.List;

import org.brahmakumaris.journeyfood.entity.AggregateJourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyFoodOrderRepository extends JpaRepository<JourneyFoodOrder, Long>{
	
	@Override
	void delete(JourneyFoodOrder order);

	@Modifying
	@Query("update JourneyFoodOrder j set j.orderStatus =:orderStatus where j.orderId =:orderId")
	void updateOrderStatus(@Param("orderId")long orderId, @Param("orderStatus")String orderStatus);

	List<JourneyFoodOrder> findByOrderStatus(String  orderStatus);
	
	Page<JourneyFoodOrder> findByOrderStatus(String  orderStatus, Pageable pageable);
	
	@Query("SELECT new JourneyFoodOrder(j.orderId, j.headCount,j.dateOfOrderPlaced,j.dateOfDeparture,j.mealRetrievalDate,j.mealRetrievalTime,j.orderStatus,j.thepla,"
			+ "j.puri,j.roti,j.achar,j.jam,j.bread,j.items) from JourneyFoodOrder j WHERE  j.mealRetrievalDate = :mealRetrievalDate AND j.mealRetrievalTime=:mealRetrievalTime AND j.orderStatus='PLACED'")
	List<JourneyFoodOrder> findByMealRetrievalDateAndMealRetrievalTimeAndOrderStatus(LocalDate mealRetrievalDate, String mealRetrievalTime);
	
	@Query("SELECT new JourneyFoodOrder(j.orderId, j.headCount,j.dateOfOrderPlaced,j.dateOfDeparture,j.mealRetrievalDate,j.mealRetrievalTime,j.orderStatus,j.thepla,"
			+ "j.puri,j.roti,j.achar,j.jam,j.bread,j.items) from JourneyFoodOrder j WHERE j.user.userId=:userId AND j.orderStatus='PLACED'")
	List<JourneyFoodOrder> findPlacedOrderByUserId(@Param("userId") long userId);
	
	@Query("SELECT new JourneyFoodOrder(j.orderId, j.headCount,j.dateOfOrderPlaced,j.dateOfDeparture,j.mealRetrievalDate,j.mealRetrievalTime,j.orderStatus,j.thepla,"
			+ "j.puri,j.roti,j.achar,j.jam,j.bread,j.items) from JourneyFoodOrder j WHERE j.user.userId=:userId AND j.orderStatus='PLACED'")
	Page<JourneyFoodOrder> findPlacedOrderByUserId(@Param("userId") long userId, Pageable pageable);
	
	@Query("SELECT new JourneyFoodOrder(j.orderId, j.headCount,j.dateOfOrderPlaced,j.dateOfDeparture,j.mealRetrievalDate,j.mealRetrievalTime,j.orderStatus,j.thepla,"
			+ "j.puri,j.roti,j.achar,j.jam,j.bread,j.items) from JourneyFoodOrder j WHERE j.user.userId=:userId")
	List<JourneyFoodOrder> findAllOrderByUserId(@Param("userId") long userId);
	
	@Query("SELECT new JourneyFoodOrder(j.orderId, j.headCount,j.dateOfOrderPlaced,j.dateOfDeparture,j.mealRetrievalDate,j.mealRetrievalTime,j.orderStatus,j.thepla,"
			+ "j.puri,j.roti,j.achar,j.jam,j.bread,j.items) from JourneyFoodOrder j WHERE j.user.userId=:userId")
	Page<JourneyFoodOrder> findAllOrderByUserIdPaging(@Param("userId") long userId, Pageable pageable);//Pageable->will have current page and content per page
	
	@Query("SELECT new AggregateJourneyFoodOrder(SUM(headCount), SUM(bread), SUM(achar), SUM(jam) , SUM(puri), SUM(roti), SUM(thepla), mealRetrievalDate)"
			+ "  FROM JourneyFoodOrder j WHERE j.orderStatus=:orderStatus AND j.mealRetrievalDate = :mealRetrievalDate")
	AggregateJourneyFoodOrder getOrdersByDateAndNotDisabled(@Param("orderStatus")String orderStatus, @Param("mealRetrievalDate")LocalDate mealRetrievalDate);
	
	@Query("SELECT new JourneyFoodOrder(j.orderId, j.headCount,j.dateOfOrderPlaced,j.dateOfDeparture,j.mealRetrievalDate,j.mealRetrievalTime,j.orderStatus,j.thepla,"
			+ "j.puri,j.roti,j.achar,j.jam,j.bread,j.items) from JourneyFoodOrder j WHERE j.mealRetrievalDate = :mealRetrievalDate")
	List<JourneyFoodOrder> getOrdersByDate(LocalDate mealRetrievalDate);
	
	@Query("SELECT new JourneyFoodOrder(j.orderId, j.headCount,j.dateOfOrderPlaced,j.dateOfDeparture,j.mealRetrievalDate,j.mealRetrievalTime,j.orderStatus,j.thepla,"
			+ "j.puri,j.roti,j.achar,j.jam,j.bread,j.items) from JourneyFoodOrder j WHERE j.mealRetrievalDate = :mealRetrievalDate")
	Page<JourneyFoodOrder> getOrdersByDate(LocalDate mealRetrievalDate, Pageable pageable);
	
	@Query("SELECT new JourneyFoodOrder(j.orderId, j.headCount,j.dateOfOrderPlaced,j.dateOfDeparture,j.mealRetrievalDate,j.mealRetrievalTime,j.orderStatus,j.thepla,"
			+ " j.puri,j.roti,j.achar,j.jam,j.bread,j.items) FROM JourneyFoodOrder j WHERE j.orderStatus=:orderStatus AND j.mealRetrievalDate = :mealRetrievalDate")
	List<JourneyFoodOrder> getOrdersByDate(@Param("mealRetrievalDate")LocalDate mealRetrievalDate, @Param("orderStatus")String orderStatus);

	@Query("SELECT new JourneyFoodOrder(j.orderId, j.headCount,j.dateOfOrderPlaced,j.dateOfDeparture,j.mealRetrievalDate,j.mealRetrievalTime,j.orderStatus,j.thepla,"
			+ " j.puri,j.roti,j.achar,j.jam,j.bread,j.items) FROM JourneyFoodOrder j WHERE j.orderStatus=:orderStatus AND j.mealRetrievalDate = :mealRetrievalDate")
	Page<JourneyFoodOrder> getOrdersByDate(@Param("mealRetrievalDate")LocalDate mealRetrievalDate, @Param("orderStatus")String orderStatus, Pageable pageable);

	
	@Query("SELECT new JourneyFoodOrder(j.orderId, j.headCount,j.dateOfOrderPlaced,j.dateOfDeparture,j.mealRetrievalDate,j.mealRetrievalTime,j.orderStatus,j.thepla,"
			+ " j.puri,j.roti,j.achar,j.jam,j.bread,j.items) FROM JourneyFoodOrder j WHERE j.orderStatus=:orderStatus AND j.mealRetrievalDate >= :fromDate AND j.mealRetrievalDate <= :endDate")
	List<JourneyFoodOrder> getOrdersByDateRangeAndOrderStatus(LocalDate fromDate, LocalDate endDate, String orderStatus);
	
	@Query("SELECT new JourneyFoodOrder(j.orderId, j.headCount,j.dateOfOrderPlaced,j.dateOfDeparture,j.mealRetrievalDate,j.mealRetrievalTime,j.orderStatus,j.thepla,"
			+ " j.puri,j.roti,j.achar,j.jam,j.bread,j.items) FROM JourneyFoodOrder j WHERE j.orderStatus=:orderStatus AND j.mealRetrievalDate >= :fromDate AND j.mealRetrievalDate <= :endDate")
	Page<JourneyFoodOrder> getOrdersByDateRangeAndOrderStatus(LocalDate fromDate, LocalDate endDate, String orderStatus, Pageable pageable);
	
	@Query("SELECT new JourneyFoodOrder(j.orderId, j.headCount,j.dateOfOrderPlaced,j.dateOfDeparture,j.mealRetrievalDate,j.mealRetrievalTime,j.orderStatus,j.thepla,"
			+ " j.puri,j.roti,j.achar,j.jam,j.bread,j.items) FROM JourneyFoodOrder j WHERE j.mealRetrievalDate >= :fromDate AND j.mealRetrievalDate <= :endDate")
	List<JourneyFoodOrder> getOrdersByDateRange(LocalDate fromDate, LocalDate endDate);
	
	@Query("SELECT new JourneyFoodOrder(j.orderId, j.headCount,j.dateOfOrderPlaced,j.dateOfDeparture,j.mealRetrievalDate,j.mealRetrievalTime,j.orderStatus,j.thepla,"
			+ " j.puri,j.roti,j.achar,j.jam,j.bread,j.items) FROM JourneyFoodOrder j WHERE j.mealRetrievalDate >= :fromDate AND j.mealRetrievalDate <= :endDate")
	Page<JourneyFoodOrder> getOrdersByDateRange(LocalDate fromDate, LocalDate endDate, Pageable pageable);

}