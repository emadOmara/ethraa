package net.pd.ethraa.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.business.GroupService;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.integration.jackson.Views;
import net.pd.ethraa.integration.response.BaseResponse;

@RestController()
@RequestMapping(path = "api/groups")
public class GroupController extends BaseController {

    @Autowired
    private GroupService groupService;

    @JsonView(Views.Group.class)
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public BaseResponse listGroups() {
	BaseResponse response = new BaseResponse();
	try {
	    List<Group> groupList = groupService.getAllGroupsWithPendingRequestCount();
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
	    group = groupService.saveGroup(group);
	    handleSuccessResponse(response, group);

	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public BaseResponse updateGroup(@RequestBody Group group) {

	BaseResponse response = new BaseResponse();
	try {
	    if (group.isNew()) {
		throw new EthraaException(EthraaConstants.ERROR_MSG_ID_CAN_T_BE_NULL);
	    }

	    groupService.saveGroup(group);
	    handleSuccessResponse(response, null);

	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public BaseResponse deleteGroup(@PathVariable("id") Long id) {

	BaseResponse response = new BaseResponse();
	try {
	    handleNullID(id);

	    groupService.deleteGroup(id);
	    handleSuccessResponse(response, null);

	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

    // TODO test this second for lazy loaded collections
    @JsonView(Views.Public.class)
    @RequestMapping(path = "/listMembers/{groupID}", method = RequestMethod.GET)
    public BaseResponse listMembers(@PathVariable("groupID") Long groupID) {

	BaseResponse response = new BaseResponse();
	try {
	    if (groupID == null || groupID == 0) {
		throw new EthraaException(EthraaConstants.ERROR_MSG_ID_CAN_T_BE_NULL);
	    }
	    List<Account> accounts = groupService.getGroupMembers(new Long(groupID));
	    handleSuccessResponse(response, accounts);

	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

}
