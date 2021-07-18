package org.brahmakumaris.journeyfood.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//https://www.codejava.net/frameworks/spring-boot/user-registration-and-login-tutorial
//https://www.pixeltrice.com/send-otpone-time-password-using-spring-boot-application-for-authentication/
//This annotation indicates that class having one or more methods which tagged with @Bean annotation, which means the return type of the method is Object or Bean.
//Login workflow -->https://www.javadevjournal.com/spring-security/spring-security-login/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	
	@Bean 
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder()	;
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http)throws Exception{
		http.authorizeRequests()
//		.antMatchers("/users").authenticated()
//		.antMatchers("/addJourneyFoodOrder/**").hasAnyAuthority("ADMIN", "EDITOR")
//		.antMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
//		.antMatchers("/delete/**").hasAuthority("ADMIN")
		.antMatchers("/addJourneyFoodOrder").authenticated()
		.antMatchers("/edit/**").authenticated()
		.antMatchers("/delete/**").authenticated()
		.antMatchers("/h2-console/**","/login","/register").permitAll()
//		.antMatchers("/users","/orders","/addJourneyFoodOrder").access("hasRole('ROLE_ADMIN')")
		.and()
		.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/login")
			.usernameParameter("email")
			.passwordParameter("password")
			.defaultSuccessUrl("/addJourneyFoodOrder")
            .failureUrl("/login?error=true")
		.and()
		.logout().logoutSuccessUrl("/login").permitAll()
		.and()
        .exceptionHandling().accessDeniedPage("/403");
		http.csrf().disable();
		http.headers().frameOptions().disable();		
	}
	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
			.antMatchers("/resources/**", "/static/**");
	}
}
