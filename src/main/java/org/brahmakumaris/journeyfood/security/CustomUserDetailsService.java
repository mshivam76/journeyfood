package org.brahmakumaris.journeyfood.security;

import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//https://www.baeldung.com/role-and-privilege-for-spring-security-registration-->User login via privileges(Role based login)
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userRepo.findByEmail(email); 
		System.out.println("Email: "+email);
		System.out.println("Email: "+user);
		if(user==null) {
			throw new UsernameNotFoundException(email+" is not registered, please register before logging in.");
		}
		return new CustomUserDetails(user);
	}

	
}
