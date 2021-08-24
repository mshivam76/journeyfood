package org.brahmakumaris.journeyfood.security;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.brahmakumaris.journeyfood.entity.JourneyFoodOrder;
import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.UserSignUpFormData;
import org.brahmakumaris.journeyfood.security.exceptions.InvalidTokenException;
import org.brahmakumaris.journeyfood.security.exceptions.UserAlreadyExistException;

public interface UserService {
    boolean checkIfUserExist(final String email);
//    void sendRegistrationConfirmationEmail(final UserEntity user);
    boolean verifyUser(final String token) throws InvalidTokenException;
//    UserEntity getUserByToken(final String token) throws InvalidTokenException;
//    UserEntity getUserById(final String id) throws UnkownIdentifierException;
	boolean verifyUserResetPassword(String token) throws InvalidTokenException;
	UserEntity register(UserSignUpFormData user, String link)throws UserAlreadyExistException, MessagingException, UnsupportedEncodingException;
	UserEntity getUser(String email);
}
