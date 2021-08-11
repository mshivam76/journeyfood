package org.brahmakumaris.journeyfood.security;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.brahmakumaris.journeyfood.entity.Privilege;
import org.brahmakumaris.journeyfood.entity.Role;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.UserSignUpFormData;
import org.brahmakumaris.journeyfood.repository.PrivilegeRepository;
import org.brahmakumaris.journeyfood.repository.RoleRepository;
import org.brahmakumaris.journeyfood.repository.UserRepository;
import org.brahmakumaris.journeyfood.security.exceptions.InvalidTokenException;
import org.brahmakumaris.journeyfood.security.exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

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
	private JavaMailSender mailSender;

	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Autowired
    private SecureTokenService secureTokenService;

    @Autowired
    SecureTokenRepository secureTokenRepository;
    
    @Value("${site.base.url.https}")
    private String baseURL;
    
	@Override
	public UserEntity register(UserSignUpFormData user) throws UserAlreadyExistException, MessagingException, UnsupportedEncodingException{
		if(checkIfUserExist(user.getEmail())) {
			System.out.println("-------------------------------------------------------------------User already exists");
			throw new  UserAlreadyExistException("User already exists for this email :"+user.getEmail());
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setContactNoOfGuide(user.getContactNoOfGuide());
		userEntity.setEmail(user.getEmail());
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

	private void sendRegistrationConfirmationEmail(UserEntity user)	throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getEmail();
		String fromAddress = "noreply.bkjourneyfood@gmail.com";
		String senderName = user.getNameOfGuide();
		String subject = "Please verify your registration";
		String content = "Dear [[name]],<br>"
				+ "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
				+ "Thank you,<br>"
				+ "Your company name.";
		SecureToken secureToken= secureTokenService.createSecureToken();
		secureToken.setUser(user);
      	secureTokenRepository.save(secureToken);
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);
		content = content.replace("[[name]]", user.getNameOfGuide());
		String verifyURL = UriComponentsBuilder.fromHttpUrl(baseURL).path("/verify").queryParam("token", secureToken.getToken()).toUriString();
		content = content.replace("[[URL]]", verifyURL);
		helper.setText(content, true);
		mailSender.send(message);
		System.out.println("Email has been sent");
	}

	@Override
	public boolean verifyUser(String token) throws InvalidTokenException {
		SecureToken secureToken = secureTokenService.findByToken(token);
		if(java.util.Objects.isNull(secureToken)||!StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()) {
			throw new InvalidTokenException("Token is not valid");
		}
		UserEntity user = userRepository.getOne(secureToken.getUser().getId());
		if(Objects.isNull(user)) {
			return false;
		}
		user.setEnabled(true);
		userRepository.save(user);
		secureTokenService.removeToken(secureToken);
		return true;
	}

    private void encodePassword( UserEntity userEntity, UserSignUpFormData user){
        userEntity.setPassword(bcryptEncoder.encode(user.getPassword()));
    }
}
