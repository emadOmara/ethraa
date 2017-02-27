package net.pd.ethraa.integration.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.integration.jackson.Views;

public class BookReaderWrapper implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1395918555510719966L;

    public BookReaderWrapper(Account account, Long points) {
	this.account = account;
	this.points = points;
    }

    @JsonView(Views.Public.class)
    private Account account;
    @JsonView(Views.Public.class)
    private long points;

    public Account getAccount() {
	return account;
    }

    public void setAccount(Account account) {
	this.account = account;
    }

}
