package net.pd.ethraa.business;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import net.pd.ethraa.common.model.Account;

@Service
public class TokenManagementService {

    private Map<String, Account> accounts = new ConcurrentHashMap<>();
    private Map<String, UserDetails> users = new ConcurrentHashMap<>();

    public Account getAccount(String token) {
	return accounts.get(token);
    }

    public void addToken(String token, Account account) {
	accounts.put(token, account);
    }

    public void removeToken(String token) {
	accounts.remove(token);
    }

    public void addUser(String token, UserDetails user) {
	users.put(token, user);
    }

    public UserDetails getUser(String token) {
	return users.get(token);
    }

    public void deleteUser(String token) {
	users.remove(token);
    }
}
