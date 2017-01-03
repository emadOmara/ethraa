package net.pd.ethraa.integration.request;

import java.util.List;

public class MessageRequest extends BaseRequest {

    /**
     *
     */
    private static final long serialVersionUID = -6722613235349888870L;

    protected String msg;

    private Long sender;

    private List<Long> recipientUsers;
    private List<Long> recipientGroups;

    public String getMsg() {
	return msg;
    }

    public void setMsg(String msg) {
	this.msg = msg;
    }

    public Long getSender() {
	return sender;
    }

    public void setSender(Long sender) {
	this.sender = sender;
    }

    public List<Long> getRecipientUsers() {
	return recipientUsers;
    }

    public void setRecipientUsers(List<Long> recipientUsers) {
	this.recipientUsers = recipientUsers;
    }

    public List<Long> getRecipientGroups() {
	return recipientGroups;
    }

    public void setRecipientGroups(List<Long> recipientGroups) {
	this.recipientGroups = recipientGroups;
    }

}