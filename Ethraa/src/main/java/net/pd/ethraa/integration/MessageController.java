package net.pd.ethraa.integration;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.business.MessageService;
import net.pd.ethraa.common.EthraaException;
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
	 * @throws EthraaException
	 */
	@RequestMapping(path = "/user/send", method = RequestMethod.POST)
	public BaseResponse sendUserMessage(@Valid @RequestBody MessageRequest request) throws EthraaException {

		BaseResponse response = new BaseResponse();
		Message msg = new Message();

		msg.setToAdmin(true);
		msg.setNewAdminMessage(true);
		msg.setMsg(request.getMsg());

		Account acc = new Account();
		acc.setId(request.getSender());
		msg.setSender(acc);

		messageService.sendMessage(msg);
		handleSuccessResponse(response, null);

		return response;

	}

	@RequestMapping(path = "/admin/send", method = RequestMethod.POST)
	public BaseResponse sendFromAdmin(@Valid @RequestBody MessageRequest msg) throws EthraaException {

		BaseResponse response = new BaseResponse();

		messageService.sendMessage(msg);
		handleSuccessResponse(response, null);

		return response;

	}

	/**
	 * retrieve the list of {@link Message}s of the user that was sent from him
	 * , from the administraion to him , or from administration to a group which
	 * he is a member of .
	 *
	 * @param msg
	 * @return
	 * @throws EthraaException
	 */
	@JsonView(Views.UserMessage.class)
	@GetMapping(path = "/user/list/{id}")
	public BaseResponse listUserMessages(@PathVariable("id") Long id) throws EthraaException {

		BaseResponse response = new BaseResponse();
		handleNullID(id);

		List<Message> userMessages = messageService.getUserMessages(id);
		handleSuccessResponse(response, userMessages);

		return response;

	}

	@JsonView(Views.UserMessage.class)
	@GetMapping(path = "/user/readNew/{id}")
	public BaseResponse listNewUserMessages(@PathVariable("id") Long id) throws EthraaException {

		BaseResponse response = new BaseResponse();
		handleNullID(id);

		List<Message> userMessages = messageService.readNewUserMessages(id);
		handleSuccessResponse(response, userMessages);

		return response;

	}

	@JsonView(Views.AdminMessage.class)
	@GetMapping(path = "/admin/list")
	public BaseResponse listAdminMessages() throws EthraaException {

		BaseResponse response = new BaseResponse();

		List<Message> messages = messageService.getAdminMessages();

		// TODO this is just atest
		// messages = messageService.getAllAdminMessagesForUser(3l, 2l);
		handleSuccessResponse(response, messages);

		return response;

	}

	/**
	 * get new messages to admins from some user
	 *
	 * @return
	 * @throws EthraaException
	 */
	@JsonView(Views.AdminMessage.class)
	@GetMapping(path = "/admin/listNew/{userId}")
	public BaseResponse listAdminMessages(@PathVariable("userId") Long userId) throws EthraaException {

		handleNullID(userId);
		BaseResponse response = new BaseResponse();

		List<Message> messages = messageService.getNewAdminMessagesForUser(userId);
		handleSuccessResponse(response, messages);

		return response;
	}

	/**
	 * count new messages to admin
	 *
	 * @return
	 * @throws EthraaException
	 */
	@GetMapping(path = "/admin/countNew")
	public BaseResponse countAdminNewMessages() throws EthraaException {

		BaseResponse response = new BaseResponse();

		Long count = messageService.countNewAdminMessages();
		handleSuccessResponse(response, count);

		return response;
	}

	/**
	 * count new messages to admin
	 *
	 * @return
	 * @throws EthraaException
	 */
	@GetMapping(path = "/user/countNew/{userId}")
	public BaseResponse countNewUserMessaes(@PathVariable("userId") Long userId) throws EthraaException {

		BaseResponse response = new BaseResponse();
		handleNullID(userId);
		Long count = messageService.countNewUserMessages(userId);
		handleSuccessResponse(response, count);

		return response;
	}

	/**
	 * get new messages to admins from some user
	 *
	 * @return
	 * @throws EthraaException
	 */
	@JsonView(Views.AdminMessage.class)
	@GetMapping(path = "/admin/list/{adminId}/{userId}")
	public BaseResponse getAllMessagesBetweenAdminAndUser(@PathVariable("adminId") Long adminId,
			@PathVariable("userId") Long userId) throws EthraaException {

		handleNullID(userId);
		BaseResponse response = new BaseResponse();

		List<Message> messages = messageService.getAllMessagesBetweenAdminAndUser(adminId, userId);
		handleSuccessResponse(response, messages);

		return response;

	}

	@JsonView(Views.Messaging.class)
	@GetMapping(path = "/user/get/{userID}/{messageID}")
	public BaseResponse readUserMessage(@PathVariable("userID") Long userID, @PathVariable("messageID") Long messageID)
			throws EthraaException {

		BaseResponse response = new BaseResponse();
		handleNullID(userID);
		handleNullID(messageID);

		Message msg = messageService.readUserMessage(userID, messageID);
		handleSuccessResponse(response, msg);

		return response;

	}

	@JsonView(Views.Messaging.class)
	@GetMapping(path = "/admin/get/{messageID}")
	public BaseResponse readAdminMessage(@PathVariable("messageID") Long messageID) throws EthraaException {

		BaseResponse response = new BaseResponse();
		handleNullID(messageID);

		Message msg = messageService.readAdminMessage(messageID);
		handleSuccessResponse(response, msg);

		return response;

	}

}
