package net.pd.ethraa.integration.response;

public class LoginResponse extends BaseResponse {

    /**
     *
     */
    private static final long serialVersionUID = 1395918555510719966L;

    public LoginResponse() {
	super();
    }

    public LoginResponse(String status, String comment) {
	super(status, comment);
    }

    private String token;

    public String getToken() {
	return token;
    }

    public void setToken(String token) {
	this.token = token;
    }

}
