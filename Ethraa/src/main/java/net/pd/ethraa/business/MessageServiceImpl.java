package net.pd.ethraa.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Message;
import net.pd.ethraa.dao.MessageDao;
import net.pd.ethraa.integration.request.MessageRequest;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

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
    public List<Message> getAdminMessages(Long id) {
	throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public void sendMessage(MessageRequest request) throws EthraaException {

	// String msg = request.getMsg();
	// List<Long> recipientGroups = request.getRecipientGroups();
	// List<Long> recipientUsers = request.getRecipientUsers();
	// Long sender = request.getSender();
	// List<MessageRecipients>
    }

}
