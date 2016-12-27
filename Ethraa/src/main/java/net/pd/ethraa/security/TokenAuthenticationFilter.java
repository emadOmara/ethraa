package net.pd.ethraa.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import net.pd.ethraa.business.TokenManagementService;

public class TokenAuthenticationFilter extends GenericFilterBean {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenManagementService tokenManagementService;

    public TokenAuthenticationFilter() {
    }

    @Override
    public void doFilter(ServletRequest obj1, ServletResponse obj2, FilterChain chain)
	    throws IOException, ServletException {
	HttpServletRequest request = (HttpServletRequest) obj1;
	HttpServletResponse response = (HttpServletResponse) obj2;
	String token = request.getHeader("xa-token");
	if (token != null) {
	    // determine the user based on the (already validated) token
	    UserDetails account = tokenManagementService.getUser(token);
	    if (account == null) {
		response.setStatus(401);
		throw new UsernameNotFoundException("could not find the user");
	    } else {

		// build an Authentication object with the user's info
		String credentials = account.getUsername() + "-" + account.getPassword();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
			credentials, account.getPassword());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		// set the authentication into the SecurityContext
		try {
		    SecurityContextHolder.getContext()
			    .setAuthentication(authenticationManager.authenticate(authentication));
		} catch (AuthenticationException e) {
		    response.setStatus(401);
		    throw new UsernameNotFoundException("could not find the user");
		}

	    }
	}
	chain.doFilter(request, response);

    }
}
