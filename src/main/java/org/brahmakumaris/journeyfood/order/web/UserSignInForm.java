package org.brahmakumaris.journeyfood.order.web;

import javax.validation.constraints.NotBlank;

public class UserSignInForm {
	
    @NotBlank(message="Email is mandatory")
    private String email;
    @NotBlank(message="Password is mandatory")
    private String password;
	
	public UserCreationParameters toParamsSignIn() {
		return new UserCreationParameters(email, password);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserSignInForm [email=" + email + ", password=" + password + "]";
	}
	
}
