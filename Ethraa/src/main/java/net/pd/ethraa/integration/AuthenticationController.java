package net.pd.ethraa.integration;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.pd.ethraa.business.AccountService;
import net.pd.ethraa.business.TokenManagementService;
import net.pd.ethraa.common.CommonUtil;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.AccountStatus;
import net.pd.ethraa.integration.response.BaseResponse;
import net.pd.ethraa.integration.response.LoginResponse;

@RestController()
@RequestMapping(path = "api/authentication")
public class AuthenticationController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenManagementService tokenManagementService;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public BaseResponse register(@RequestBody Account account) {

	BaseResponse response = new BaseResponse();
	try {
	    accountService.add(account);
	    response.setStatus(EthraaConstants.OK);
	    response.setComment(EthraaConstants.GENERAL_SUCCESS);

	} catch (EthraaException e) {
	    response.setStatus(EthraaConstants.ERROR);
	    response.setComment(e.getMessage());
	}

	return response;

    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody Account account) {

	UserDetails userDetails = null;
	LoginResponse response = null;
	try {

	    if (account == null || StringUtils.isEmpty(account.getMobile())
		    || StringUtils.isEmpty(account.getPassword())) {
		throw new UsernameNotFoundException("Invalid User Name");
	    }

	    String credentials = account.getMobile() + "-" + account.getPassword();
	    userDetails = userDetailsService.loadUserByUsername(credentials);

	    Account fetchedAccount = accountService.findUserWithPermissions(account.getMobile(),
		    userDetails.getPassword(), AccountStatus.ACTIVE);
	    String token = CommonUtil.generateToken(account);
	    tokenManagementService.addUser(userDetails.getUsername(), token, userDetails);

	    response = new LoginResponse(EthraaConstants.OK, EthraaConstants.GENERAL_SUCCESS);
	    response.setToken(token);
	    response.setResult(fetchedAccount.getPermissions());

	} catch (Exception e) {
	    response.setStatus(EthraaConstants.ERROR);
	    response.setComment(e.getMessage());

	}
	return response;
    }

}
