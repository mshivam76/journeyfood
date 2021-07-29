package org.brahmakumaris.journeyfood.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.brahmakumaris.journeyfood.entity.Privilege;
import org.brahmakumaris.journeyfood.entity.Role;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//https://www.baeldung.com/role-and-privilege-for-spring-security-registration-->User login via privileges(Role based login)
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity user = userRepo.findByEmail(email); 
		System.out.println("Email: "+email);
		System.out.println("Email: "+user);
		if(user==null) {
			throw new UsernameNotFoundException(email+" is not registered, please register before logging in.");
		}
//		return new CustomUserDetails(user);
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user.getRoles()));

	}

	private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
		return getGrantedAuthorities(getPrivileges(roles));
	}
	private List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> privileges = new ArrayList<>();
        final List<Privilege> collection = new ArrayList<>();
        for (final Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (final Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }
	
    private List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
