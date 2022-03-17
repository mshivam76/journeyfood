package org.brahmakumaris.journeyfood.repository;

import java.time.LocalDate;
import java.util.List;

import org.brahmakumaris.journeyfood.entity.SpecialItemForADate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialItemForADateRepository extends JpaRepository<SpecialItemForADate, Long> {

	@Override
	void delete(SpecialItemForADate item);
	
	List<SpecialItemForADate> findByDateOfOrder(LocalDate date);
	
	SpecialItemForADate findByServingDate(LocalDate date);
	
	List<SpecialItemForADate> findByServingDateAndServingSlot(LocalDate date, String slot);
	
	List<SpecialItemForADate> findByServingSlot(String slot);
}
