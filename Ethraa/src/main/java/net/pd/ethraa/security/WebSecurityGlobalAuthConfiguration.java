package net.pd.ethraa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class WebSecurityGlobalAuthConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

}

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
class TokenAuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO return the implementation after the demo

		http.authorizeRequests().anyRequest().permitAll();
		// http.authorizeRequests().antMatchers("/api/authentication/**").permitAll();
		// http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic().and().csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().httpBasic().and().csrf()
				.disable();

		// ====================================================
		// http.authorizeRequests().antMatchers("/api/authentication/**").permitAll();
		// http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic().and().csrf().disable();
		// http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// http.addFilterBefore(authenticationFilter(),
		// BasicAuthenticationFilter.class);
	}

	@Bean
	public TokenAuthenticationFilter authenticationFilter() throws Exception {
		return new TokenAuthenticationFilter();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
