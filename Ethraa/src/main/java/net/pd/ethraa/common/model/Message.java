// package net.pd.ethraa.common.model;
//
// import java.util.Date;
// import java.util.List;
//
// import javax.persistence.Entity;
// import javax.persistence.ManyToOne;
// import javax.persistence.OneToMany;
// import javax.persistence.Temporal;
// import javax.persistence.TemporalType;
//
/// ****
// * Message Entity****
// *
// * @author Emad
// *
// */
// @Entity
// public class Message extends BaseEntity {
//
// /**
// *
// */
//
// private static final long serialVersionUID = 5105914722614237201L;
//
// protected String msg;
//
// @ManyToOne
// protected Account sender;
//
// @OneToMany(mappedBy = "msg")
// private List<MessageRecipients> recipients;
//
// @Temporal(TemporalType.TIMESTAMP)
// private Date creationDate;
//
// public Account getSender() {
// return sender;
// }
//
// public void setSender(Account sender) {
// this.sender = sender;
// }
//
// public Date getCreationDate() {
// return creationDate;
// }
//
// public void setCreationDate(Date creationDate) {
// this.creationDate = creationDate;
// }
//
// public String getMsg() {
// return msg;
// }
//
// public void setMsg(String msg) {
// this.msg = msg;
// }
//
// public List<MessageRecipients> getRecipients() {
// return recipients;
// }
//
// public void setRecipients(List<MessageRecipients> recipients) {
// this.recipients = recipients;
// }
//
// }