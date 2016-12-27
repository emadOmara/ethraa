package net.pd.ethraa.business;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TokenManagementService {

    private Map<String, UserDetailsWrapper> users = new ConcurrentHashMap<>();

    public void addUser(String mobile, String token, UserDetails user) {
	users.put(token, new UserDetailsWrapper(token, user));
    }

    public UserDetails getUser(String mobile) {
	UserDetailsWrapper userDetailsWrapper = users.get(mobile);
	if (userDetailsWrapper != null) {
	    return userDetailsWrapper.getUser();
	}
	return null;
    }

    public void deleteUser(String mobile) {
	users.remove(mobile);
    }

    private class UserDetailsWrapper {
	private UserDetails user;
	private String token;

	public UserDetailsWrapper(String token, UserDetails userDetails) {
	    this.token = token;
	    user = userDetails;
	}

	public UserDetails getUser() {
	    return user;
	}

	public void setUser(UserDetails user) {
	    this.user = user;
	}

    }
}
