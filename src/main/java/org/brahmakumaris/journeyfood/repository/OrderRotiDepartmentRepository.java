package org.brahmakumaris.journeyfood.repository;

import java.time.LocalDate;
import java.util.Date;

import org.brahmakumaris.journeyfood.entity.OrderForRotiDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRotiDepartmentRepository extends JpaRepository<OrderForRotiDepartment, Long> {
	OrderForRotiDepartment findByOrderForDate(LocalDate date);
	OrderForRotiDepartment findByOrderDate(Date date);
	OrderForRotiDepartment findByRotiDeptId(Long id);
}
