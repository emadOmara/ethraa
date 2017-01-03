package net.pd.ethraa.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Message> getUserMessages(Long id) throws EthraaException {

	try {

	    List<Message> userMessages = messageDao.getUserMessages(id);
	    return userMessages;
	} catch (Exception e) {
	    throw new EthraaException(e);
	}

    }

    @Override
    // add sent from him
    public List<Message> getAdminMessages(Long id) throws EthraaException {
	try {

	    List<Message> messages = messageDao.findByToAdminTrue();
	    return messages;
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    @Override
    public void sendMessage(MessageRequest request) throws EthraaException {

	try {
	    Message message = new Message();
	    message.setMsg(request.getMsg());

	    Long senderID = request.getSender();
	    Long groupID = request.getGroup();
	    Long userID = request.getUser();

	    Account sender = new Account();
	    sender.setId(senderID);
	    message.setSender(sender);

	    List<Account> users = new ArrayList<>();
	    if (groupID != null && groupID > 0) {
		Group g = new Group();
		g.setId(groupID);
		message.setGroup(g);
		users = accountDao.findByGroupId(groupID);
	    } else {
		Account acc = new Account();
		acc.setId(userID);
		users.add(acc);
	    }

	    List<MessageRecipients> recipients = new ArrayList<>();

	    for (Account acc : users) {
		MessageRecipients item = new MessageRecipients(message, acc);
		recipients.add(item);
	    }

	    message.setRecipients(recipients);
	    Message save = messageDao.save(message);

	} catch (Exception e) {
	    throw new EthraaException(e);
	}

    }

}
