package org.brahmakumaris.journeyfood.repository;

import java.time.LocalDate;
import java.util.Date;

import org.brahmakumaris.journeyfood.entity.OrderForBreadDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBreadDeptRepository extends JpaRepository<OrderForBreadDepartment, Long> {
	OrderForBreadDepartment findByOrderForDate(LocalDate date);
	OrderForBreadDepartment findByOrderDate(Date date);
}
