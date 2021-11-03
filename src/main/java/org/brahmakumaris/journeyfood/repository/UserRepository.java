package org.brahmakumaris.journeyfood.repository;

import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	public UserEntity findByEmail(String email);
	@Override
    void delete(UserEntity user);
	
	@Query("SELECT u from UserEntity u where nameOfGuide like %?1%")
	List<UserEntity> findByName(String name);
}
