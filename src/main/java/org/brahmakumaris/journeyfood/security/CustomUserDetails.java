package org.brahmakumaris.journeyfood.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.brahmakumaris.journeyfood.entity.Role;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

	private UserEntity user;

	/*
	 * private Collection<? extends GrantedAuthority> getAuthorities(final Set<Role>
	 * roles) { return getGrantedAuthorities(getPrivileges(roles)); }
	 * 
	 * private List<String> getPrivileges(final Set<Role> roles) { final
	 * List<String> privileges = new ArrayList<>(); final List<Privilege> collection
	 * = new ArrayList<>(); for (final Role role : roles) {
	 * privileges.add(role.getName()); collection.addAll(role.getPrivileges()); }
	 * for (final Privilege item : collection) { privileges.add(item.getName()); }
	 * return privileges; }
	 * 
	 * private List<GrantedAuthority> getGrantedAuthorities(final List<String>
	 * privileges) { final List<GrantedAuthority> authorities = new ArrayList<>();
	 * for (final String privilege : privileges) { authorities.add(new
	 * SimpleGrantedAuthority(privilege)); } return authorities; }
	 */	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
	}

	
	public CustomUserDetails(UserEntity user) {
		super();
		this.user = user;
	}

    public long getId() {
        return user.getUserId();
    }

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}
	
	public String getNameOfGuide() {
		return user.getNameOfGuide() ;
	}
}