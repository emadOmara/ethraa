package net.pd.ethraa.integration.response;

import java.io.Serializable;

public class BaseResponse implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 948366373511442642L;
    private String status;
    private String comment;
    private Object result;

    public BaseResponse() {
    }

    public BaseResponse(String status, String comment) {
	this.status = status;
	this.comment = comment;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getComment() {
	return comment;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

    public Object getResult() {
	return result;
    }

    public void setResult(Object result) {
	this.result = result;
    }

}
