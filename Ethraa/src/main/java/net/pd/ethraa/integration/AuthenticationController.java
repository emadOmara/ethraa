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

import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.business.AccountService;
import net.pd.ethraa.business.TokenManagementService;
import net.pd.ethraa.common.CommonUtil;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.AccountStatus;
import net.pd.ethraa.common.model.AccountType;
import net.pd.ethraa.integration.jackson.Views;
import net.pd.ethraa.integration.response.BaseResponse;
import net.pd.ethraa.integration.response.LoginResponse;

@RestController()
@RequestMapping(path = "api/authentication")
public class AuthenticationController extends BaseController {

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
	    account.setAccountType(AccountType.NORMAL);
	    account.setAccountStatus(AccountStatus.INACTIVE);
	    accountService.saveAccount(account);
	    handleSuccessResponse(response, null);

	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

    @JsonView(Views.LoginSuccess.class)
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody Account account) {

	UserDetails userDetails = null;
	LoginResponse response = new LoginResponse();
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
	    response.setResult(fetchedAccount);

	} catch (Exception e) {
	    handleFailureResponse(response, e);

	}
	return response;
    }

}
