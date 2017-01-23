package net.pd.ethraa.integration.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AttendenceRequest extends BaseRequest {

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
    @Min(1)
    protected Long dayId;

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

    public Long getDayId() {
	return dayId;
    }

    public void setDayId(Long dayId) {
	this.dayId = dayId;
    }

}