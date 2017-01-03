package net.pd.ethraa.common.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.integration.jackson.Views;

/*****
 * Message Entity******
 *
 * @author Emad
 *
 */
@Entity
public class Message extends BaseEntity {

    /**
    *
    */

    private static final long serialVersionUID = 5105914722614237201L;

    @JsonView(Views.Messaging.class)
    protected String msg;

    @ManyToOne
    protected Account sender;

    @OneToMany(mappedBy = "msg")
    private List<MessageRecipients> recipients;

    @JsonView(Views.Messaging.class)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @ManyToOne
    private Group group;

    private boolean toAdmin = false;

    public Account getSender() {
	return sender;
    }

    public void setSender(Account sender) {
	this.sender = sender;
    }

    public Date getCreationDate() {
	return creationDate;
    }

    public void setCreationDate(Date creationDate) {
	this.creationDate = creationDate;
    }

    public String getMsg() {
	return msg;
    }

    public void setMsg(String msg) {
	this.msg = msg;
    }

    public List<MessageRecipients> getRecipients() {
	return recipients;
    }

    public void setRecipients(List<MessageRecipients> recipients) {
	this.recipients = recipients;
    }

    public boolean isToAdmin() {
	return toAdmin;
    }

    public void setToAdmin(boolean toAdmin) {
	this.toAdmin = toAdmin;
    }

    @JsonView(Views.Messaging.class)
    @Transient
    public String getSenderDisplayName() {
	if (group != null) {
	    return group.getName();
	}
	return sender.getUserName();
    }
}