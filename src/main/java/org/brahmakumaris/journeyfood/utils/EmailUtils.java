package org.brahmakumaris.journeyfood.utils;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.brahmakumaris.journeyfood.security.SecureToken;
import org.brahmakumaris.journeyfood.security.SecureTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class EmailUtils {
	private static Logger LOGGER = LoggerFactory.getLogger(EmailUtils.class);
	private static String fromAddress = "noreply.bkjourneyfood@gmail.com";
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
    private SecureTokenService secureTokenService;
	
    @Value("${email.user.verify.subject}")
    private String verificationSubject;

    @Value("${email.password.reset.content}")
    private String passwordResetContent;
	
	public void sendEmailVerificationMail(UserEntity user, String link) throws MessagingException, UnsupportedEncodingException {
		LOGGER.info("EmailUtils- sendEmailVerificationMail()  : starts");
		String toAddress = user.getEmail();
		String senderName = user.getNameOfGuide();
		String subject = verificationSubject;
		String content = "Dear [[name]],<br>"
				+ "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
				+ "Thank you,<br>"
				+ "<b>Brahmakumaris</b>";
		SecureToken secureToken= secureTokenService.createSecureToken();
		secureToken.setUser(user);
      	secureTokenService.saveSecureToken(secureToken);
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
	
	public void sendPasswordResetEmail(UserEntity user, String link)throws MessagingException, UnsupportedEncodingException {
		LOGGER.info("DefaultUserService- sendPasswordResetEmail() : starts");
		String toAddress = user.getEmail();
		
		String senderName = user.getNameOfGuide();
		String subject = "Brahmakumaris Journeyfood - Reset password";
		String content = passwordResetContent;
		SecureToken secureToken= secureTokenService.createSecureToken();
		secureToken.setUser(user);
		secureTokenService.saveSecureToken(secureToken);
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
}
