package net.pd.ethraa.business;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TokenManagementService {

	private Map<String, UserDetails> users = new ConcurrentHashMap<>();

	public void addUser(String token, UserDetails user) {
		users.put(token, user);
	}

	public UserDetails getUser(String token) {
		UserDetails UserDetails = users.get(token);
		return UserDetails;

	}

	public void deleteUser(String mobile) {
		for (Iterator<UserDetails> iterator = users.values().iterator(); iterator.hasNext();) {
			UserDetails userDetails = iterator.next();
			if (userDetails != null && userDetails.getUsername().equals(mobile)) {
				iterator.remove();
			}

		}
	}

}
