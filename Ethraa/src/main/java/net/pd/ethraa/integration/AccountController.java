package net.pd.ethraa.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.pd.ethraa.business.GenericService;
import net.pd.ethraa.common.BaseResponse;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;

@RestController()
@RequestMapping(path = "api/account")
public class AccountController {

    @Autowired
    private GenericService<Account> genericService;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public BaseResponse register(@RequestBody Account account) {

	BaseResponse response = new BaseResponse();
	try {
	    genericService.add(account);
	    response.setStatus(EthraaConstants.OK);

	} catch (EthraaException e) {
	    response.setStatus(EthraaConstants.ERROR);
	    response.setComment(e.getMessage());
	}

	return response;

    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody Account account) {

	BaseResponse response = new BaseResponse();

	return response;

    }

}
