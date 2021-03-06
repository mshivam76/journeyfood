package org.brahmakumaris.journeyfood.repository;


import org.brahmakumaris.journeyfood.entity.SpecialItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialItemRepository extends JpaRepository<SpecialItem, Integer> {
	SpecialItem findByItem(String item);
}
