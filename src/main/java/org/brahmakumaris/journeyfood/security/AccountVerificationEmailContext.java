package org.brahmakumaris.journeyfood.security;

import org.brahmakumaris.journeyfood.entity.UserEntity;
import org.springframework.web.util.UriComponentsBuilder;

public class AccountVerificationEmailContext extends AbstractEmailContext{
	 private String token;


	    @Override
	    public <T> void init(T context){
	        //we can do any common configuration setup here
	        // like setting up some base URL and context
	        UserEntity user = (UserEntity) context; // we pass the customer informati
	        put("firstName", user.getEmail());
	        setTemplateLocation("emails/email-verification");
	        setSubject("Complete your registration");
	        setFrom("no-reply@javadevjournal.com");
	        setTo(user.getEmail());
	    }

	    public void setToken(String token) {
	        this.token = token;
	        put("token", token);
	    }

	    public void buildVerificationUrl(final String baseURL, final String token){
	        final String url= UriComponentsBuilder.fromHttpUrl(baseURL)
	                .path("/register/verify").queryParam("token", token).toUriString();
	        put("verificationURL", url);
	    }
}
