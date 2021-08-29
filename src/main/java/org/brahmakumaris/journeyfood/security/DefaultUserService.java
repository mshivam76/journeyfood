package org.brahmakumaris.journeyfood.security;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.Privilege;
import org.brahmakumaris.journeyfood.entity.Role;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.CreateJourneyFoodOrderFormData;
import org.brahmakumaris.journeyfood.order.web.UserSignUpFormData;
import org.brahmakumaris.journeyfood.repository.PrivilegeRepository;
import org.brahmakumaris.journeyfood.repository.RoleRepository;
import org.brahmakumaris.journeyfood.repository.UserRepository;
import org.brahmakumaris.journeyfood.security.exceptions.InvalidTokenException;
import org.brahmakumaris.journeyfood.security.exceptions.UserAlreadyExistException;
import org.brahmakumaris.journeyfood.security.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static Logger LOGGER = LoggerFactory.getLogger(DefaultUserService.class);
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
	public UserEntity register(UserSignUpFormData user, String link) throws UserAlreadyExistException, MessagingException, UnsupportedEncodingException{
		LOGGER.info("DefaultUserService - register() Entered -User=> "+user);
		if(checkIfUserExist(user.getEmail())) {
			LOGGER.error("-------------------------------------------------------------------User already exists");
			throw new  UserAlreadyExistException("User already exists for this email :"+user.getEmail());
		}
		UserEntity userEntity=null;
		try {
			userEntity = new UserEntity();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userEntity.setContactNoOfGuide(user.getContactNoOfGuide());
		userEntity.setEmail(user.getEmail());
		userEntity.setNameOfCenter(user.getNameOfCenter());
		userEntity.setNameOfGuide(user.getNameOfGuide());
		userEntity.setZone(user.getZone());
		userEntity.setSubZone(user.getSubZone());
		userEntity.setPincode(user.getPincode());
		final Role userRole =  createRoleIfNotFound("ROLE_USER", assignPrivilege(userEntity));
		encodePassword(userEntity, user);//Encoding and setting password
		userEntity.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		sendEmailVerificationMail(userEntity, link);
		LOGGER.info("DefaultUserService - register User Exit => "+user);
		return userRepository.save(userEntity);
	}
	
	@Override
	public boolean checkIfUserExist(String email) {
		LOGGER.info("DefaultUserService - checkIfUserExist Entered ");
		return userRepository.findByEmail(email) !=null ? true : false;
	}

    @Transactional
    List<Privilege> assignPrivilege(UserEntity user) {
    	LOGGER.info("DefaultUserService- assignPrivilege()- start");
	    final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
	    final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
	    final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");
	    final List<Privilege> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
	    LOGGER.info("DefaultUserService- assignPrivilege()  : ends");
	    return userPrivileges;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(final String name) {
    	LOGGER.info("DefaultUserService- createPrivilegeIfNotFound()  : start");
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilege = privilegeRepository.save(privilege);
        }
        LOGGER.info("DefaultUserService- createPrivilegeIfNotFound()  : ends");
        return privilege;
    }
    
    @Transactional
    Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
    	LOGGER.info("DefaultUserService- createRoleIfNotFound()  : start");
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.setPrivileges(privileges);
        role = roleRepository.save(role);
        LOGGER.info("DefaultUserService- createRoleIfNotFound()  : ends");
        return role;
    }

	private void sendEmailVerificationMail(UserEntity user, String link) throws MessagingException, UnsupportedEncodingException {
		LOGGER.info("DefaultUserService- sendEmailVerificationMail()  : starts");
		String toAddress = user.getEmail();
		String fromAddress = "noreply.bkjourneyfood@gmail.com";
		String senderName = user.getNameOfGuide();
		String subject = "Brahmakumaris Journeyfood - Email verification";
		String content = "Dear [[name]],<br>"
				+ "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
				+ "Thank you,<br>"
				+ "<b>Brahmakumaris</b>";
		SecureToken secureToken= secureTokenService.createSecureToken();
		secureToken.setUser(user);
      	secureTokenRepository.save(secureToken);
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);
		content = content.replace("[[name]]", user.getNameOfGuide());
		String verifyURL = UriComponentsBuilder.fromHttpUrl(link).path("/verify").queryParam("token", secureToken.getToken()).toUriString();
		content = content.replace("[[URL]]", verifyURL);
		helper.setText(content, true);
		mailSender.send(message);
		LOGGER.info("Email has been sent");
	}
	
	public static String getSiteURL(HttpServletRequest request) {
		LOGGER.info("DefaultUserService getSiteURL enter");
        String siteURL = request.getRequestURL().toString();
        LOGGER.info("DefaultUserService getSiteURL exit");
        return siteURL.replace(request.getServletPath(), "");
    }
	@Override
	public boolean verifyUser(String token) throws InvalidTokenException {
		LOGGER.info("DefaultUserService verifyUser start");
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
		LOGGER.info("DefaultUserService verifyUser exit");
		return true;
	}
	
	@Override
	public boolean verifyUserResetPassword(String token) throws InvalidTokenException {
		LOGGER.info("DefaultUserService- verifyUserResetPassword() : starts");
		SecureToken secureToken = secureTokenService.findByToken(token);
		if(java.util.Objects.isNull(secureToken)||!StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()) {
			LOGGER.info("DefaultUserService- verifyUserResetPassword()- Verification failed : ends");
			throw new InvalidTokenException("Token is not valid");
		}
		UserEntity user = userRepository.getOne(secureToken.getUser().getId());
		if(Objects.isNull(user)) {
			LOGGER.info("DefaultUserService- verifyUserResetPassword()- Verification failed : ends");
			return false;
		}
		LOGGER.info("DefaultUserService- verifyUserResetPassword()-Verification Successful : ends");
		return true;
	}

	@Override
	public UserEntity getUser(String email) {
		return userRepository.findByEmail(email);
	}
	
    private void encodePassword( UserEntity userEntity, UserSignUpFormData user){
        userEntity.setPassword(bcryptEncoder.encode(user.getPassword()));
    }
    
    public void forgotPassword(String email, String link) throws UserNotFoundException, UnsupportedEncodingException, MessagingException {
    	LOGGER.info("DefaultUserService- forgotPassword() : starts");
    	UserEntity user = userRepository.findByEmail(email);
        if (checkIfUserExist(email)) {
        	sendPasswordResetEmail(user, link);
        } else throw new UserNotFoundException("Unregistered email - " + email+" .Please register first.");
        LOGGER.info("DefaultUserService- forgotPassword() : ends");
    } 
    
	private void sendPasswordResetEmail(UserEntity user, String link)throws MessagingException, UnsupportedEncodingException {
		LOGGER.info("DefaultUserService- sendPasswordResetEmail() : starts");
		String toAddress = user.getEmail();
		String fromAddress = "noreply.bkjourneyfood@gmail.com";
		String senderName = user.getNameOfGuide();
		String subject = "Brahmakumaris Journeyfood - Reset password";
		String content = "Dear [[name]],<br>"
				+ "Please click the link below to reset your password:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">RESET PASSWORD</a></h3>"
				+ "Thank you,<br>"
				+ "<b>Brahmakumaris</b>";
		SecureToken secureToken= secureTokenService.createSecureToken();
		secureToken.setUser(user);
      	secureTokenRepository.save(secureToken);
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);
		content = content.replace("[[name]]", user.getNameOfGuide());
		String verifyURL = UriComponentsBuilder.fromHttpUrl(link).path("/verifyUserResetPassword").queryParam("token", secureToken.getToken()).toUriString();
		content = content.replace("[[URL]]", verifyURL);
		helper.setText(content, true);
		mailSender.send(message);
		LOGGER.info("DefaultUserService- sendPasswordResetEmail() ends : Reset Password Email has been sent successfully=======================================================================================================================================");
	}
    
    public UserEntity getByResetPasswordToken(String token) {
    	LOGGER.info("DefaultUserService - getByResetPasswordToken Exit- token: "+token);
    	UserEntity user= secureTokenRepository.findByToken(token).getUser();
    	secureTokenRepository.removeByToken(token);//Deleting token here after getting data
    	LOGGER.info("DefaultUserService - getByResetPasswordToken Exit- User: "+user);
    	return user;
    }
     
    public void updatePassword(UserEntity user, String newPassword) {
    	LOGGER.info("DefaultUserService - updatePassword Entered ");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);//After resetting password token deletion is not required as token is already deleted after returning UserEntity POJO in getByResetPasswordToken();
        LOGGER.info("DefaultUserService - updatePassword() saved succesfully -Exit");
    }

	@Override
	public List<UserEntity> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public UserEntity getUser(long id) {
		return userRepository.getOne(id);
	}
	
	@Override
	public void deleteUser(long id) {
		Optional<UserEntity> order = userRepository.findById(id);
		if(order.isPresent())
			userRepository.deleteById(id);
		else 
			throw new IllegalArgumentException("Unable to find user with id - "+id);
	}

	@Override
	public void updateUser(UserSignUpFormData user) throws IllegalArgumentException{
		LOGGER.info("JourneyFoodServiceImpl updateOrder method - Enter ");
		UserEntity userPOJO = getUser(user.getId());
		userPOJO.setNameOfCenter(user.getNameOfCenter());
		userPOJO.setNameOfGuide(user.getNameOfGuide());
		userPOJO.setContactNoOfGuide(user.getContactNoOfGuide()); 
		userPOJO.setEmail(user.getEmail()); 
		userPOJO.setZone(user.getZone());
		userPOJO.setSubZone(user.getSubZone());
		userPOJO.setPincode(user.getPincode());
		userPOJO.setPassword(user.getPassword());
		LOGGER.info("JourneyFoodServiceImpl updateOrder method - Exit =>order(user/null): "+ userRepository.save(userPOJO));
	}
}