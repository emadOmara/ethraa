package net.pd.ethraa.security;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import net.pd.ethraa.business.AccountService;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Permission;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String credentials) throws UsernameNotFoundException {

	if (StringUtils.isEmpty(credentials)) {
	    throw new UsernameNotFoundException("Empty username");
	}

	String mobile = null, password = null;
	int splitIndx = credentials.indexOf("-");
	if (splitIndx != -1) {
	    mobile = credentials.substring(0, splitIndx);
	    password = credentials.substring(splitIndx + 1, credentials.length());
	} else {
	    throw new UsernameNotFoundException("Invalid User Name");
	}

	Account account = null;

	try {
	    account = accountService.findUserWithPermissions(mobile, password, EthraaConstants.ACTIVE);
	} catch (EthraaException e) {
	    e.printStackTrace();
	}
	if (account != null) {
	    return new User(account.getMobile(), account.getPassword(), true, true, true, true,
		    AuthorityUtils.createAuthorityList(getAuthorities(account)));
	} else {
	    throw new UsernameNotFoundException("could not find the user '" + mobile + "'");
	}

    }

    private String[] getAuthorities(Account account) {
	List<Permission> permissions = account.getPermissions();
	String[] result = new String[permissions.size()];

	for (int i = 0; i < permissions.size(); i++) {
	    result[i] = permissions.get(i).getName();

	}

	return result;
    }
}
