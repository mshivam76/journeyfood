package org.brahmakumaris.journeyfood.repository;

import org.brahmakumaris.journeyfood.entity.Privilege;
import org.brahmakumaris.journeyfood.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);

    @Override
    void delete(Role role);
}
