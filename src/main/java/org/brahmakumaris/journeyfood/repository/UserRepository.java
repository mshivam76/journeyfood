package org.brahmakumaris.journeyfood.repository;

import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	public UserEntity findByEmail(String email);
	@Override
    void delete(UserEntity user);
}
