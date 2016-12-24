package net.pd.ethraa.common;

import java.io.Serializable;

public class BaseResponse implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 948366373511442642L;
    private String status;
    private String comment;

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

}
