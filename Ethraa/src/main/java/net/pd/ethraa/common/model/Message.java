// package net.pd.ethraa.common.model;
//
// import java.util.Date;
//
// import javax.persistence.ManyToOne;
// import javax.persistence.MappedSuperclass;
// import javax.persistence.Temporal;
// import javax.persistence.TemporalType;
//
/// **
// * Message Entity
// *
// * @author Emad
// *
// */
// @MappedSuperclass
// public class Message extends BaseEntity {
//
// /**
// *
// */
//
// private static final long serialVersionUID = 5105914722614237201L;
//
// protected String subject;
//
// protected String body;
//
// @ManyToOne
// protected Account sender;
//
// @ManyToOne
// protected Account receiver;
//
// @Temporal(TemporalType.TIMESTAMP)
// private Date date;
//
// public String getSubject() {
// return subject;
// }
//
// public void setSubject(String subject) {
// this.subject = subject;
// }
//
// public String getBody() {
// return body;
// }
//
// public void setBody(String body) {
// this.body = body;
// }
//
// public Account getSender() {
// return sender;
// }
//
// public void setSender(Account sender) {
// this.sender = sender;
// }
//
// public Account getReceiver() {
// return receiver;
// }
//
// public void setReceiver(Account receiver) {
// this.receiver = receiver;
// }
//
// }