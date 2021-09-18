package org.brahmakumaris.journeyfood.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.brahmakumaris.journeyfood.security.DefaultUserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceImpl {
	@InjectMocks
	DefaultUserService service;
	
	@Test
	public void disableUserTest() {
		
		assertTrue(service.disableUser(10));
	}
}
