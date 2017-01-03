package net.pd.ethraa.integration.request;

public class MessageRequest extends BaseRequest {

    /**
     *
     */
    private static final long serialVersionUID = -6722613235349888870L;

    protected String msg;

    private Long sender;

    private Long user;
    private Long group;

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

    public Long getUser() {
	return user;
    }

    public void setUser(Long user) {
	this.user = user;
    }

    public Long getGroup() {
	return group;
    }

    public void setGroup(Long group) {
	this.group = group;
    }

}