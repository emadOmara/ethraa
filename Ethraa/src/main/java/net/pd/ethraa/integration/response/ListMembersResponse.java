package net.pd.ethraa.integration.response;

import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.integration.jackson.Views;

public class ListMembersResponse extends BaseResponse {

    /**
     *
     */
    private static final long serialVersionUID = 1395918555510719966L;

    public ListMembersResponse() {
	super();
    }

    public ListMembersResponse(String status, String comment) {
	super(status, comment);
    }

    @JsonView(Views.Public.class)
    private Integer pendingAccounts;

    public Integer getPendingAccounts() {
	return pendingAccounts;
    }

    public void setPendingAccounts(Integer pendingAccounts) {
	this.pendingAccounts = pendingAccounts;
    }

}
