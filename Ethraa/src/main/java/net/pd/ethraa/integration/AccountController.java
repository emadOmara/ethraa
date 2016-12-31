package net.pd.ethraa.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.pd.ethraa.business.AccountService;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.AccountStatus;
import net.pd.ethraa.common.model.AccountType;
import net.pd.ethraa.integration.response.BaseResponse;

@RestController()
@RequestMapping(path = "api/account")
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountService;

    /**
     * This action is used by admins to add new admin user
     *
     * @param account
     * @return
     */
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public BaseResponse addAccount(@RequestBody Account account) {

	BaseResponse response = new BaseResponse();
	try {
	    account.setAccountStatus(AccountStatus.ACTIVE);
	    account.setAccountType(AccountType.ADMIN);
	    accountService.saveAccount(account);
	    handleSuccessResponse(response, null);
	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public BaseResponse editAccount(@RequestBody Account account) {

	BaseResponse response = new BaseResponse();
	try {
	    if (account.isNew()) {
		throw new EthraaException(EthraaConstants.ERROR_MSG_ID_CAN_T_BE_NULL);
	    }

	    accountService.saveAccount(account);
	    handleSuccessResponse(response, null);
	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public BaseResponse deleteAccount(@PathVariable("id") Long id) {

	BaseResponse response = new BaseResponse();
	try {
	    accountService.deleteAccount(id);
	    handleSuccessResponse(response, null);
	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

}
