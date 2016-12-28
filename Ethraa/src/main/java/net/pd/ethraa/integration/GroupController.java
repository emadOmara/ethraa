package net.pd.ethraa.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.pd.ethraa.business.AccountService;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.integration.response.BaseResponse;

@RestController()
@RequestMapping(path = "api/groups")
public class GroupController extends BaseController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public BaseResponse listGroups() {

	BaseResponse response = new BaseResponse();
	try {
	    List<Group> groupList = accountService.getAllGroups();
	    handleSuccessResponse(response, groupList);

	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public BaseResponse addGroup(@RequestBody Group group) {

	BaseResponse response = new BaseResponse();
	try {
	    accountService.saveGroup(group);
	    handleSuccessResponse(response, null);

	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public BaseResponse updateGroup(@RequestBody Group group) {

	BaseResponse response = new BaseResponse();
	try {
	    if (group.isNew()) {
		throw new EthraaException(EthraaConstants.ERROR_MSG_ID_CAN_T_BE_NULL);
	    }

	    accountService.saveGroup(group);
	    handleSuccessResponse(response, null);

	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

    @RequestMapping(path = "/deleteGroup/{id}", method = RequestMethod.GET)
    public BaseResponse deleteGroup(@PathVariable("id") Long id) {

	BaseResponse response = new BaseResponse();
	try {

	    accountService.deleteGroup(id);
	    response.setStatus(EthraaConstants.OK);
	    response.setComment(EthraaConstants.GENERAL_SUCCESS);

	} catch (Exception e) {
	    response.setStatus(EthraaConstants.ERROR);
	    response.setComment(e.getMessage());
	}

	return response;

    }

}
