package org.brahmakumaris.journeyfood.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.mail.MessagingException;

import org.brahmakumaris.journeyfood.entity.Privilege;
import org.brahmakumaris.journeyfood.entity.Role;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.UserSignUpFormData;
import org.brahmakumaris.journeyfood.repository.PrivilegeRepository;
import org.brahmakumaris.journeyfood.repository.RoleRepository;
import org.brahmakumaris.journeyfood.repository.UserRepository;
import org.brahmakumaris.journeyfood.security.exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service("UserService")
@Transactional
public class DefaultUserService implements UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Autowired
    private SecureTokenService secureTokenService;

    @Autowired
    SecureTokenRepository secureTokenRepository;
    
    @Autowired
    EmailService emailService;
    

    @Value("${site.base.url.https}")
    private String baseURL;
    
	@Override
	public UserEntity register(UserSignUpFormData user) throws UserAlreadyExistException {
		if(checkIfUserExist(user.getEmail())) {
			System.out.println("-------------------------------------------------------------------User already exists");
			throw new  UserAlreadyExistException("User already exists for this email :"+user.getEmail());
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setContactNoOfGuide(user.getContactNoOfGuide());
		userEntity.setEmail(user.getEmail());
		userEntity.setEnabled(true);
		userEntity.setNameOfCenter(user.getNameOfCenter());
		userEntity.setNameOfGuide(user.getNameOfGuide());
		final Role userRole =  createRoleIfNotFound("ROLE_USER", assignPrivilege(userEntity));
		encodePassword(userEntity, user);
		userEntity.setRoles(Arrays.asList(userRole));
		sendRegistrationConfirmationEmail(userEntity);
		return userRepository.save(userEntity);
		
	}
	
	@Override
	public boolean checkIfUserExist(String email) {
		return userRepository.findByEmail(email) !=null ? true : false;
	}

    @Transactional
    List<Privilege> assignPrivilege(UserEntity user) {
	    final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
	    final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
	    final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");
	    final List<Privilege> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
	    return userPrivileges;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilege = privilegeRepository.save(privilege);
        }
        return privilege;
    }
    
    @Transactional
    Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.setPrivileges(privileges);
        role = roleRepository.save(role);
        return role;
    }

	@Override
	public void sendRegistrationConfirmationEmail(UserEntity user) {
		SecureToken secureToken= secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenRepository.save(secureToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

	}
//
//	@Override
//	public boolean verifyUser(String token) throws InvalidTokenException {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public User getUserById(String id) throws UnkownIdentifierException {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
    private void encodePassword( UserEntity userEntity, UserSignUpFormData user){
        userEntity.setPassword(bcryptEncoder.encode(user.getPassword()));
    }
}
