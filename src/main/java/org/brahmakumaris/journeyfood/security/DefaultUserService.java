package org.brahmakumaris.journeyfood.security;

import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.UserSignUpFormData;
import org.brahmakumaris.journeyfood.repository.UserRepository;
import org.brahmakumaris.journeyfood.security.exceptions.UserAlreadyExistException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service("UserService")
@Transactional
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository userRepository;
    
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserEntity register(UserSignUpFormData user) throws UserAlreadyExistException {
		if(checkIfUserExist(user.getEmail())) {
			System.out.println("-------------------------------------------------------------------User already exists");
			throw new  UserAlreadyExistException("User already exists for this email :"+user.getEmail());
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setContactNoOfGuide(user.getContactNoOfGuide());
		userEntity.setEmail(user.getEmail());
		userEntity.setNameOfCenter(user.getNameOfCenter());
		userEntity.setNameOfGuide(user.getNameOfGuide());
		encodePassword(userEntity, user);
		return userRepository.save(userEntity);
	}

	@Override
	public boolean checkIfUserExist(String email) {
		return userRepository.findByEmail(email) !=null ? true : false;
	}

//	@Override
//	public void sendRegistrationConfirmationEmail(User user) {
//		// TODO Auto-generated method stub
//
//	}
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
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
