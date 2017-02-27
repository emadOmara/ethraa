package net.pd.ethraa.integration.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EvaluationRequest extends BaseRequest {

    /**
     *
     */
    private static final long serialVersionUID = -4588108412410537805L;

    @NotNull
    @Min(1)
    protected Long bookId;

    @NotNull
    @Min(1)
    protected Long userId;

    @NotNull
    @Min(0)
    protected Long grade;

    public Long getBookId() {
	return bookId;
    }

    public void setBookId(Long bookId) {
	this.bookId = bookId;
    }

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public Long getGrade() {
	return grade;
    }

    public void setGrade(Long grade) {
	this.grade = grade;
    }

}