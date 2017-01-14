package net.pd.ethraa.integration.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class MessageRequest extends BaseRequest {

    /**
     *
     */
    private static final long serialVersionUID = -6722613235349888870L;

    @NotNull
    @NotEmpty
    protected String msg;
    @NotNull
    private Long sender;

    private List<Long> users;
    private List<Long> groups;

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

    public List<Long> getUsers() {
	return users;
    }

    public void setUsers(List<Long> users) {
	this.users = users;
    }

    public List<Long> getGroups() {
	return groups;
    }

    public void setGroups(List<Long> groups) {
	this.groups = groups;
    }

}