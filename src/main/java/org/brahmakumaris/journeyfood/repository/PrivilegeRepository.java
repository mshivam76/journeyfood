package org.brahmakumaris.journeyfood.repository;

import org.brahmakumaris.journeyfood.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
	Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);
}
