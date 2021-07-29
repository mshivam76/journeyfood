package org.brahmakumaris.journeyfood.entity;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="users")
public class UserEntity {
	@Id
	@Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
	
	@Column(nullable = false, length = 100)
    private String nameOfCenter;

	@Column(nullable = false, length = 100)
    private String nameOfGuide;
    
	@Column(nullable = false, length = 18)
    private String contactNoOfGuide;
    
	@Column(nullable = false, unique = true, length = 70)
    private String email;
    
	@Column(nullable = false, length = 150)
    private String password;
	
	@Column(nullable = false, columnDefinition = "TINYINT DEFAULT false")
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean enabled;//whether account is verified using email or not
	
	@OneToMany(mappedBy="user")
    private Set tokens;
	 
    public  UserEntity(UserEntity user) {
        super();
        this.id = user.getId();
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

	public void setId(final long id) {
		this.id = id;
	}

	public String getNameOfCenter() {
		return nameOfCenter;
	}

	public void setNameOfCenter(final String nameOfCenter) {
		this.nameOfCenter = nameOfCenter;
	}

	public String getNameOfGuide() {
		return nameOfGuide;
	}

	public void setNameOfGuide(final String nameOfGuide) {
		this.nameOfGuide = nameOfGuide;
	}

	public String getContactNoOfGuide() {
		return contactNoOfGuide;
	}

	public void setContactNoOfGuide(final String contactNoOfGuide) {
		this.contactNoOfGuide = contactNoOfGuide;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
	
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Collection<Role> roles) {
        this.roles = roles;
    }
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = true;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", roles=" + roles + ", nameOfCenter=" + nameOfCenter + ", nameOfGuide="
				+ nameOfGuide + ", contactNoOfGuide=" + contactNoOfGuide + ", email=" + email + ", password=" + password
				+ ", enabled=" + enabled + "]";
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((getEmail() == null) ? 0 : getEmail().hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserEntity user = (UserEntity) obj;
        if (!getEmail().equals(user.getEmail())) {
            return false;
        }
        return true;
    }
}