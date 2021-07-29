package org.brahmakumaris.journeyfood.security;

import javax.mail.MessagingException;

public interface EmailService {
	void sendMail(final AbstractEmailContext email) throws MessagingException;
}