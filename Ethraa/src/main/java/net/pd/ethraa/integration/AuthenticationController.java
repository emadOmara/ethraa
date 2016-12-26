package net.pd.ethraa.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.pd.ethraa.business.AccountService;
import net.pd.ethraa.business.TokenManagementService;
import net.pd.ethraa.common.BaseResponse;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;

@RestController()
@RequestMapping(path = "api/authentication")
public class AuthenticationController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenManagementService cachManagementService;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public BaseResponse register(@RequestBody Account account) {

	BaseResponse response = new BaseResponse();
	try {
	    accountService.add(account);
	    response.setStatus(EthraaConstants.OK);

	} catch (EthraaException e) {
	    response.setStatus(EthraaConstants.ERROR);
	    response.setComment(e.getMessage());
	}

	return response;

    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody Account account) {

	Account fetchedAccount = null;
	UserDetails userDetails = null;

	userDetails = userDetailsService.loadUserByUsername(account.getMobile());

	// fetchedAccount =
	// accountService.findUserWithPermissions(account.getMobile());

	if (userDetails != null) {
	    String token = "teeeeeeeeeeeeeeeeeeeeeeeeeet";
	    // fetchedAccount.setToken(token);
	    cachManagementService.addUser(token, userDetails);
	}

	BaseResponse response = new BaseResponse();

	return response;
    }

    @RequestMapping(path = "/login123", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER123')")
    public BaseResponse login123(@RequestBody Account account) {

	BaseResponse response = new BaseResponse();

	return response;

    }

    @RequestMapping(path = "/login234", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public BaseResponse login234(@RequestBody Account account) {

	BaseResponse response = new BaseResponse();

	return response;

    }

}
