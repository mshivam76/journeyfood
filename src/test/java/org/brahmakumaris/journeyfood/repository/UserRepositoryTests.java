package org.brahmakumaris.journeyfood.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
 
    @Autowired
    private TestEntityManager entityManager;
     
    @Autowired
    private UserRepository repo;
     
    // test methods go below
    @Test
    public void testCreateuser() {
    	UserEntity user =  new UserEntity();
    	user.setContactNoOfGuide("contactNoOfGuide");
    	user.setEmail("email@email.com");
    	user.setNameOfCenter("nameOfCenter");
    	user.setNameOfGuide("nameOfGuide");
    	user.setPassword("password");
    	UserEntity savedUser = repo.save(user);
    	UserEntity existingUser = entityManager.find(UserEntity.class, savedUser.getId());
    	assertThat(user.getEmail()).isEqualTo(existingUser.getEmail());
    }
}