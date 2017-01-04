package net.pd.ethraa.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.business.MessageService;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Message;
import net.pd.ethraa.integration.jackson.Views;
import net.pd.ethraa.integration.request.MessageRequest;
import net.pd.ethraa.integration.response.BaseResponse;

@RestController()
@RequestMapping(path = "api/messages")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    /**
     * Normal user sends to admin
     *
     * @param msg
     * @return
     */
    @RequestMapping(path = "/user/send", method = RequestMethod.POST)
    public BaseResponse sendUserMessage(@RequestBody MessageRequest request) {

	BaseResponse response = new BaseResponse();
	try {
	    Message msg = new Message();

	    msg.setToAdmin(true);
	    msg.setNewAdminMessage(true);
	    msg.setMsg(request.getMsg());

	    Account acc = new Account();
	    acc.setId(request.getSender());
	    msg.setSender(acc);

	    messageService.sendMessage(msg);
	    handleSuccessResponse(response, null);

	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

    @RequestMapping(path = "/admin/send", method = RequestMethod.POST)
    public BaseResponse sendFromAdmin(@RequestBody MessageRequest msg) {

	BaseResponse response = new BaseResponse();
	try {

	    messageService.sendMessage(msg);
	    handleSuccessResponse(response, null);

	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

    /**
     * retrieve the list of {@link Message}s of the user that was sent from him
     * , from the administraion to him , or from administration to a group which
     * he is a member of .
     *
     * @param msg
     * @return
     */
    @JsonView(Views.Messaging.class)
    @GetMapping(path = "/user/list/{id}")
    public BaseResponse listUserMessages(@PathVariable("id") Long id) {

	BaseResponse response = new BaseResponse();
	try {
	    handleNullID(id);

	    List<Message> userMessages = messageService.getUserMessages(id);
	    handleSuccessResponse(response, userMessages);

	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

    @JsonView(Views.Messaging.class)
    @GetMapping(path = "/admin/list")
    public BaseResponse listAdminMessages() {

	BaseResponse response = new BaseResponse();
	try {

	    List<Message> messages = messageService.getAdminMessages();
	    handleSuccessResponse(response, messages);

	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

    @JsonView(Views.Messaging.class)
    @GetMapping(path = "/user/get/{userID}/{messageID}")
    public BaseResponse readUserMessage(@PathVariable("userID") Long userID,
	    @PathVariable("messageID") Long messageID) {

	BaseResponse response = new BaseResponse();
	try {
	    handleNullID(userID);
	    handleNullID(messageID);

	    Message msg = messageService.readUserMessage(userID, messageID);
	    handleSuccessResponse(response, msg);

	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

    @JsonView(Views.Messaging.class)
    @GetMapping(path = "/admin/get/{userID}/{messageID}")
    public BaseResponse readAdminMessage(@PathVariable("userID") Long userID,
	    @PathVariable("messageID") Long messageID) {

	BaseResponse response = new BaseResponse();
	try {
	    handleNullID(userID);
	    handleNullID(messageID);

	    Message msg = messageService.readAdminMessage(userID, messageID);
	    handleSuccessResponse(response, msg);

	} catch (Exception e) {
	    handleFailureResponse(response, e);
	}

	return response;

    }

}
