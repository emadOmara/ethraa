package net.pd.ethraa.business;

import java.util.List;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Message;
import net.pd.ethraa.integration.request.MessageRequest;

public interface MessageService {

    void sendMessage(Message msg) throws EthraaException;

    List<Message> getUserMessages(Long id) throws EthraaException;

    List<Message> getAdminMessages(Long id) throws EthraaException;

    void sendMessage(MessageRequest msg) throws EthraaException;

}
