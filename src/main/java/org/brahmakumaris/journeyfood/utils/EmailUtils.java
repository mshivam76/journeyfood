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
import org.springframework.mail.MailException;
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
    private String userVerificationSubject;

    @Value("${email.user.verify.content}")
    private String userVerificationContent;
    
    @Value("${email.user.update.subject}")
    private String userUpdateSubject;

    @Value("${email.user.update.content}")
    private String userUpdateContent;
    
    @Value("${email.password.reset.subject}")
    private String pwdResetSubject;

    @Value("${email.password.reset.content}")
    private String pwdResetContent;
    
    @Value("${email.order.update.subject}")
    private String orderUpdateSubject;

    @Value("${email.order.update.content}")
    private String orderUpdateContent; 
    
    @Value("${email.order.create.subject}")
    private String orderPlacedSubject;

    @Value("${email.order.create.content}")
    private String orderPlacedContent; 
    
    @Value("${email.order.delivered.subject}")
    private String orderDeliverSubject;

    @Value("${email.order.delivered.content}")
    private String orderDeliverContent;
    
    @Value("${email.order.cancelled.subject}")
    private String orderCancelledSubject;

    @Value("${email.order.cancelled.content}")
    private String orderCancelledContent;    
    
    public void orderCancelledMail(UserEntity user, Long orderId) throws MessagingException, UnsupportedEncodingException {
		LOGGER.info("EmailUtils- orderCancelledMail()  : starts");
		String toAddress = user.getEmail();
		String senderName = user.getNameOfGuide();
		String subject = orderCancelledSubject;
		String content = orderCancelledContent;
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);
		content = content.replace("[[name]]", user.getNameOfGuide());
		content = content.replace("[[orderId]]", orderId.toString());
		helper.setText(content, true);
		mailSender.send(message);
		LOGGER.info("orderCancelledMail() -Email has been sent");
	}
    
    public void orderDeliveredMail(UserEntity user, Long orderId) throws MessagingException, UnsupportedEncodingException {
		LOGGER.info("EmailUtils- orderDeliveredMail()  : starts");
		String toAddress = user.getEmail();
		String senderName = user.getNameOfGuide();
		String subject = orderDeliverSubject;
		String content = orderDeliverContent;
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);
		content = content.replace("[[name]]", user.getNameOfGuide());
		content = content.replace("[[orderId]]", orderId.toString());
		helper.setText(content, true);
		mailSender.send(message);
		LOGGER.info("orderDeliveredMail() -Email has been sent");
	}
    
    public void orderPlacedMail(UserEntity user) throws MessagingException, UnsupportedEncodingException {
		LOGGER.info("EmailUtils- orderPlacedMail()  : starts");
		String toAddress = user.getEmail();
		String senderName = user.getNameOfGuide();
		String subject = orderPlacedSubject;
		String content = orderPlacedContent;
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);
		content = content.replace("[[name]]", user.getNameOfGuide());
		helper.setText(content, true);
		mailSender.send(message);
		LOGGER.info("orderPlacedMail() -Email has been sent");
	}
    
    public void orderUpdatedMail(UserEntity user, Long orderId) throws MessagingException, UnsupportedEncodingException {
		LOGGER.info("EmailUtils- orderUpdatedMail()  : starts");
		String toAddress = user.getEmail();
		String senderName = user.getNameOfGuide();
		String subject = orderUpdateSubject;
		String content = orderUpdateContent;
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);
		content = content.replace("[[name]]", user.getNameOfGuide());
		content = content.replace("[[orderId]]", orderId.toString());
		helper.setText(content, true);
		mailSender.send(message);
		LOGGER.info("orderUpdatedMail() -Email has been sent");
	}
    
    public void userUpdatedMail(UserEntity user) throws MessagingException, UnsupportedEncodingException {
		LOGGER.info("EmailUtils- userUpdatedMail()  : starts");
		String toAddress = user.getEmail();
		String senderName = user.getNameOfGuide();
		String subject = userUpdateSubject;
		String content = userUpdateContent;
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);
		content = content.replace("[[name]]", user.getNameOfGuide());
		content = content.replace("[[userId]]", user.getUserId().toString());
		helper.setText(content, true);
		mailSender.send(message);
		LOGGER.info("userUpdatedMail() -Email has been sent");
	}
	
	public void sendEmailVerificationMail(UserEntity user, String link) throws MessagingException, UnsupportedEncodingException, MailException {
		LOGGER.info("EmailUtils- sendEmailVerificationMail()  : starts");
		String toAddress = user.getEmail();
		String senderName = user.getNameOfGuide();
		String subject = userVerificationSubject;
		String content = userVerificationContent;
		SecureToken secureToken= secureTokenService.createSecureToken();
		secureToken= secureTokenService.createSecureToken();
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
		String subject = pwdResetSubject;
		String content = pwdResetContent;
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
