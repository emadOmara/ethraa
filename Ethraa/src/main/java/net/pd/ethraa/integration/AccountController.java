package net.pd.ethraa.integration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.pd.ethraa.business.AccountService;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.AccountType;
import net.pd.ethraa.common.model.Permission;
import net.pd.ethraa.integration.response.BaseResponse;

@RestController()
@RequestMapping(path = "api/account")
public class AccountController extends BaseController {

	Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	/**
	 * This action is used by admins to add new admin user
	 *
	 * @param account
	 * @return
	 * @throws EthraaException
	 */
	@RequestMapping(path = "/add", method = RequestMethod.POST)
	public BaseResponse addAccount(@RequestBody Account account) throws EthraaException {

		BaseResponse response = new BaseResponse();

		account.setAccountStatus(EthraaConstants.ACTIVE);
		account.setAccountType(AccountType.ADMIN);
		account = accountService.saveAccount(account);
		handleSuccessResponse(response, account);

		return response;

	}

	@RequestMapping(path = "/get/{id}", method = RequestMethod.GET)
	public BaseResponse get(@PathVariable("id") Long id) throws EthraaException {

		BaseResponse response = new BaseResponse();
		handleNullID(id);

		Account account = accountService.findUserWithPermissions(id);
		handleSuccessResponse(response, account);

		return response;

	}

	@RequestMapping(path = "/edit", method = RequestMethod.POST)
	public BaseResponse editAccount(@RequestBody Account account) throws EthraaException {

		BaseResponse response = new BaseResponse();
		if (account.isNew()) {
			throw new EthraaException(EthraaConstants.ERROR_MSG_ID_CAN_T_BE_NULL);
		}

		accountService.saveAccount(account);
		handleSuccessResponse(response, null);

		return response;

	}

	@RequestMapping(path = "/activate", method = RequestMethod.POST)
	public BaseResponse activateAccount(@RequestBody Account account) throws EthraaException {

		BaseResponse response = new BaseResponse();
		if (account.isNew()) {
			throw new EthraaException(EthraaConstants.ERROR_MSG_ID_CAN_T_BE_NULL);
		}

		accountService.activateAccount(account);
		handleSuccessResponse(response, null);

		return response;

	}

	@RequestMapping(path = "/forgetPassword", method = RequestMethod.POST)
	public BaseResponse forgetPassword(@RequestBody Account account) throws EthraaException {

		BaseResponse response = new BaseResponse();
		if (account.getMobile() == null) {
			throw new EthraaException(EthraaConstants.ERROR_MSG_MOBILE_CAN_T_BE_NULL);
		}

		accountService.forgetPassword(account);
		handleSuccessResponse(response, null);

		return response;

	}

	@RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
	public BaseResponse deleteAccount(@PathVariable("id") Long id) throws EthraaException {

		BaseResponse response = new BaseResponse();
		accountService.deleteAccount(id);
		handleSuccessResponse(response, null);

		return response;

	}

	@RequestMapping(path = "/listManagers", method = RequestMethod.GET)
	public BaseResponse listMembers() throws EthraaException {

		BaseResponse response = new BaseResponse();

		List<Account> accounts = accountService.getAllAccounts(AccountType.ADMIN);
		handleSuccessResponse(response, accounts);

		return response;

	}

	@RequestMapping(path = "/search/{name}", method = RequestMethod.GET)
	public BaseResponse searchUsers(@PathVariable("name") String name) throws EthraaException {

		BaseResponse response = new BaseResponse();

		List<Account> accounts = accountService.findByUserName(AccountType.NORMAL, name);
		handleSuccessResponse(response, accounts);

		return response;

	}

	@RequestMapping(path = "/search", method = RequestMethod.GET)
	public BaseResponse searchUsers() throws EthraaException {

		BaseResponse response = new BaseResponse();

		List<Account> accounts = accountService.findByUserName(AccountType.NORMAL, null);
		handleSuccessResponse(response, accounts);

		return response;

	}

	@RequestMapping(path = "/permissions/list", method = RequestMethod.GET)
	public BaseResponse listPermissions() throws EthraaException {

		BaseResponse response = new BaseResponse();
		List<Permission> permissions = accountService.getAllPermissions();
		handleSuccessResponse(response, permissions);

		return response;

	}

	/**
	 * count Pending users
	 *
	 * @return
	 * @throws EthraaException
	 */
	@GetMapping(path = "/countPending")
	public BaseResponse countPendingUsers() throws EthraaException {

		BaseResponse response = new BaseResponse();
		Long count = accountService.countPendingUsers();
		handleSuccessResponse(response, count);

		return response;
	}

}
