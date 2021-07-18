package org.brahmakumaris.journeyfood.security;

import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.order.web.UserSignUpFormData;
import org.brahmakumaris.journeyfood.security.exceptions.UserAlreadyExistException;

public interface UserService {
	UserEntity register(final UserSignUpFormData user) throws UserAlreadyExistException;
    boolean checkIfUserExist(final String email);
//    void sendRegistrationConfirmationEmail(final UserEntity user);
//    boolean verifyUser(final String token) throws InvalidTokenException;
//    UserEntity getUserById(final String id) throws UnkownIdentifierException;
}
