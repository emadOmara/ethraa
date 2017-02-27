package net.pd.ethraa.integration.request;

import java.io.Serializable;

public class EvaluationPoint implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 8874431353000541709L;
    Long userId;
    Long point;

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public Long getPoint() {
	return point;
    }

    public void setPoint(Long point) {
	this.point = point;
    }

}