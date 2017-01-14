package net.pd.ethraa.business;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.CommonUtil;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.common.model.Message;
import net.pd.ethraa.common.model.MessageRecipients;
import net.pd.ethraa.dao.AccountDao;
import net.pd.ethraa.dao.MessageDao;
import net.pd.ethraa.integration.request.MessageRequest;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private AccountDao accountDao;

    @Override
    public void sendMessage(Message msg) throws EthraaException {
	try {
	    messageDao.save(msg);
	} catch (Exception e) {
	    throw new EthraaException(e);
	}

    }

    @Override
    public List<Message> getUserMessages(Long userID) throws EthraaException {

	try {

	    Object[] result = messageDao.getUserMessages(userID);

	    List<Message> userMessages = new ArrayList<>();
	    for (Object wrapper : result) {
		Message msg = (Message) ((Object[]) wrapper)[0];
		Boolean isNewMessage = (Boolean) ((Object[]) wrapper)[1];
		if (isNewMessage != null) {
		    msg.setNewUserMessage(isNewMessage);
		}

		userMessages.add(msg);
	    }

	    return userMessages;
	} catch (Exception e) {
	    throw new EthraaException(e);
	}

    }

    @Override
    // add sent from him
    public List<Message> getAdminMessages(Long adminId) throws EthraaException {
	try {

	    List<Message> messages = messageDao.findByToAdminTrueOrSenderIdOrderByCreationDateDesc(adminId);
	    return messages;
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    @Override
    public List<Message> getNewAdminMessages() throws EthraaException {
	try {

	    List<Message> messages = messageDao.findByToAdminTrueAndNewAdminMessageTrueOrderByCreationDateDesc();
	    return messages;
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    @Override
    public void sendMessage(MessageRequest request) throws EthraaException {

	Message message = new Message();
	message.setMsg(request.getMsg());

	List<Long> groupIds = request.getGroups();
	List<Long> userIds = request.getUsers();

	if (CommonUtil.isEmpty(groupIds) && CommonUtil.isEmpty(userIds)) {
	    throw new EthraaException("Please specify target");
	}

	addGroupMessages(request);

	addUserMessages(request);

    }

    private void addUserMessages(MessageRequest request) {

	List<Long> userIds = request.getUsers();

	if (CommonUtil.isEmpty(userIds)) {
	    return;
	}
	Long senderID = request.getSender();
	Message message = new Message();
	message.setMsg(request.getMsg());

	List<MessageRecipients> recipients = new ArrayList<>();

	for (Long userId : userIds) {
	    Account acc = new Account();
	    acc.setId(userId);

	    MessageRecipients item = new MessageRecipients(message, acc);
	    recipients.add(item);
	}

	Account sender = new Account();
	sender.setId(senderID);

	message.setSender(sender);
	message.setRecipients(recipients);
	messageDao.save(message);

    }

    private void addGroupMessages(MessageRequest request) {
	List<Long> groupIds = request.getGroups();
	if (CommonUtil.isEmpty(groupIds)) {
	    return;
	}
	Long senderID = request.getSender();

	for (Long groupId : groupIds) {
	    Message message = new Message();
	    message.setMsg(request.getMsg());

	    Group g = new Group();
	    g.setId(groupId);
	    message.setGroup(g);

	    List<Account> users = accountDao.findByGroupId(groupId);

	    if (CommonUtil.isEmpty(users)) {
		continue;
	    }
	    List<MessageRecipients> recipients = new ArrayList<>();

	    for (Account acc : users) {
		MessageRecipients item = new MessageRecipients(message, acc);
		recipients.add(item);
	    }
	    Account sender = new Account();
	    sender.setId(senderID);
	    message.setSender(sender);
	    message.setRecipients(recipients);
	    messageDao.save(message);

	}

    }

    @Override
    public Message readUserMessage(Long userID, Long messageID) throws EthraaException {

	try {

	    Message message = messageDao.findOne(messageID);
	    messageDao.updateMessageReadFlag(false, messageID);
	    return message;
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    @Override
    public Message readAdminMessage(Long messageID) throws EthraaException {
	try {

	    Message message = messageDao.findOne(messageID);
	    message.setNewAdminMessage(false);

	    message = messageDao.save(message);
	    return message;
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    @Override
    public List<Message> readNewUserMessages(Long userId) throws EthraaException {
	try {

	    List<Message> newUserMessages = messageDao.getNewUserMessages(userId);
	    for (Message message : newUserMessages) {
		message.setNewUserMessage(false);
		messageDao.updateMessageReadFlag(false, message.getId());
	    }
	    return newUserMessages;

	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

}
