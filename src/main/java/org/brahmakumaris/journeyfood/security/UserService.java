package org.brahmakumaris.journeyfood.security;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.UserSignUpFormData;
import org.brahmakumaris.journeyfood.order.web.UserUpdateForm;
import org.brahmakumaris.journeyfood.security.exceptions.InvalidTokenException;
import org.brahmakumaris.journeyfood.security.exceptions.UserAlreadyExistException;
import org.springframework.data.domain.Page;

public interface UserService {
    boolean checkIfUserExist(final String email);
//    void sendRegistrationConfirmationEmail(final UserEntity user);
    boolean verifyUser(final String token) throws InvalidTokenException;
//    UserEntity getUserByToken(final String token) throws InvalidTokenException;
//    UserEntity getUserById(final String id) throws UnkownIdentifierException;
	boolean verifyUserResetPassword(String token) throws InvalidTokenException;
	UserEntity register(UserSignUpFormData user, String link)throws UserAlreadyExistException, MessagingException, UnsupportedEncodingException;
	UserEntity getUser(String email);
	List<UserEntity> getUsers();
	UserEntity getUser(long id);
	void deleteUser(long id);
	boolean disableUser(long id);
//	void update(long id) throws IllegalArgumentException;
	void updateUser(UserUpdateForm user) throws IllegalArgumentException, UnsupportedEncodingException, MessagingException;
	boolean enableUser(long id);
	List<UserEntity> getUsersByName(String name);
	Page<UserEntity> findPaginated(int pageNo, int pageSize);
}
