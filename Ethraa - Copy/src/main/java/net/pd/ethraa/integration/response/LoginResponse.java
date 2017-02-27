package net.pd.ethraa.integration.response;

import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.integration.jackson.Views;

public class LoginResponse extends BaseResponse {

    /**
     *
     */
    private static final long serialVersionUID = 1395918555510719966L;

    public LoginResponse() {
	super();
    }

    public LoginResponse(int status, String comment) {
	super(status, comment);
    }

    @JsonView(Views.LoginSuccess.class)
    private String token;

    public String getToken() {
	return token;
    }

    public void setToken(String token) {
	this.token = token;
    }

}
