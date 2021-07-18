package org.brahmakumaris.journeyfood.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="users")
public class UserEntity {
	@Id
	@Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
//	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
//	@JoinTable(
//			name = "users_roles",
//			joinColumns = @JoinColumn(name="user_id"),
//			inverseJoinColumns = @JoinColumn(name="role_id")
//			)
//	private Set<Role> roles = new HashSet<>();
	
	@Column(nullable = false, length = 100)
    private String nameOfCenter;

	@Column(nullable = false, length = 100)
    private String nameOfGuide;
    
	@Column(nullable = false, unique = true, length = 18)
    private String contactNoOfGuide;
    
	@Column(nullable = false, unique = true, length = 70)
    private String email;
    
	@Column(nullable = false, length = 150)
    private String password;
	
	@Column(nullable = false, columnDefinition = "TINYINT DEFAULT false")
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean enabled;
	
    public  UserEntity(UserEntity user) {
        super();
        this.id = user.getId();
//        this.roles = user.getRoles();
        this.nameOfGuide = user.getNameOfGuide();
        this.email = user.getEmail();
        this.contactNoOfGuide = user.getContactNoOfGuide();
        this.enabled = isEnabled();
        this.password = user.getPassword();
    }
    	
    
	
	public UserEntity() {
		super();
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

//	public Set<Role> getRoles() {
//		return roles;
//	}
//
//	public void setRoles(Set<Role> roles) {
//		this.roles = roles;
//	}
	
	public String getNameOfCenter() {
		return nameOfCenter;
	}

	public void setNameOfCenter(String nameOfCenter) {
		this.nameOfCenter = nameOfCenter;
	}

	public String getNameOfGuide() {
		return nameOfGuide;
	}

	public void setNameOfGuide(String nameOfGuide) {
		this.nameOfGuide = nameOfGuide;
	}

	public String getContactNoOfGuide() {
		return contactNoOfGuide;
	}

	public void setContactNoOfGuide(String contactNoOfGuide) {
		this.contactNoOfGuide = contactNoOfGuide;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
//		this.enabled = enabled;
		this.enabled = true;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id /*+ ", roles=" + roles */+ ", nameOfCenter=" + nameOfCenter + ", nameOfGuide="
				+ nameOfGuide + ", contactNoOfGuide=" + contactNoOfGuide + ", email=" + email + ", password=" + password
				+ ", enabled=" + enabled + "]";
	}
    
	
}
