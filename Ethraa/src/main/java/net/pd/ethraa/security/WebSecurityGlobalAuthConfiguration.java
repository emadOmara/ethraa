package net.pd.ethraa.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import net.pd.ethraa.business.AccountService;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Permission;

@Configuration
public class WebSecurityGlobalAuthConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(userDetailsService);
    }

    @Bean
    UserDetailsService userDetailsService() {
	return mobile -> {
	    Account account = null;

	    try {
		account = accountService.findUserWithPermissions(mobile);
	    } catch (EthraaException e) {
		e.printStackTrace();
	    }
	    if (account != null) {
		return new User(account.getMobile(), account.getPassword(), true, true, true, true,
			AuthorityUtils.createAuthorityList(getAuthorities(account)));
	    } else {
		throw new UsernameNotFoundException("could not find the user '" + mobile + "'");
	    }

	};
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
