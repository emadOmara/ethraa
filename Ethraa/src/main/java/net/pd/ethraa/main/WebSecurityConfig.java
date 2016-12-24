package net.pd.ethraa.main;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests().antMatchers("/api/account/register", "/api/account/login").permitAll();
	http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic().and().csrf().disable();
	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}