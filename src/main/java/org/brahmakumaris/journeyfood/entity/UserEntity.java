package org.brahmakumaris.journeyfood.entity;

import java.util.Collection;

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
    
	@Column(nullable = false, length = 70)
    private String zone;
	
	@Column(nullable = false, length = 70)
    private String subZone;
	
	@Column(length = 10)
    private Integer pincode;
	
	@Column(nullable = false, length = 150)
    private String password;
	
	@Column(nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean enabled;//whether account is verified using email or not
	
	public UserEntity(long id, Collection<Role> roles, String nameOfCenter, String nameOfGuide, String contactNoOfGuide, String email, String zone, String subZone, Integer pincode, String password, boolean enabled) {
		super();
		this.id = id;
		this.roles = roles;
		this.nameOfCenter = nameOfCenter;
		this.nameOfGuide = nameOfGuide;
		this.contactNoOfGuide = contactNoOfGuide;
		this.email = email;
		this.zone = zone;
		this.subZone = subZone;
		this.pincode = pincode;
		this.password = password;
		this.enabled = enabled;
	}

	public UserEntity(String email) {
		super();
		this.email = email;
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

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getSubZone() {
		return subZone;
	}

	public void setSubZone(String subZone) {
		this.subZone = subZone;
	}

	public Integer getPincode() {
		return pincode;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
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
				+ nameOfGuide + ", contactNoOfGuide=" + contactNoOfGuide + ", email=" + email + ", zone=" + zone
				+ ", subZone=" + subZone + ", pincode=" + pincode + ", password=" + password + ", enabled=" + enabled
				+ "]";
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