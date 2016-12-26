package net.pd.ethraa.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;

public class XAuthTokenProcessingFilter extends GenericFilterBean {

    private UserDetailsService userService;

    public XAuthTokenProcessingFilter(UserDetailsService userDetailsService) {
	userService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
	    throws IOException, ServletException {
	System.out.println("XAuthTokenProcessingFilter.doFilter()");
    }

}
