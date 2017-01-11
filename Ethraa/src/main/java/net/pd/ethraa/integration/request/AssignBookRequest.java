package net.pd.ethraa.integration.request;

import java.util.List;

public class AssignBookRequest extends BaseRequest {

    /**
     *
     */
    private static final long serialVersionUID = -6722613235349888870L;

    protected Long bookId;

    private List<Long> groups;

    public List<Long> getGroups() {
	return groups;
    }

    public void setGroups(List<Long> groups) {
	this.groups = groups;
    }

    public Long getBookId() {
	return bookId;
    }

    public void setBookId(Long bookId) {
	this.bookId = bookId;
    }

}