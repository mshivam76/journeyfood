package org.brahmakumaris.journeyfood.repository;

import java.time.LocalDate;
import java.util.Date;

import org.brahmakumaris.journeyfood.entity.OrderForKitchenDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderKitchenDeptRepository extends JpaRepository<OrderForKitchenDepartment, Long> {
	OrderForKitchenDepartment findByOrderForDate(LocalDate date);
	OrderForKitchenDepartment findByOrderDate(Date date);
	OrderForKitchenDepartment findByKitchenDeptId(Long id);
}
