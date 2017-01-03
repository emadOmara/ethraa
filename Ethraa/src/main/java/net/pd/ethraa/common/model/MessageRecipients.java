package net.pd.ethraa.common.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/*****Message Entity******

@author Emad
 *
 */
 @Entity
 public class MessageRecipients extends BaseEntity {

 private static final long serialVersionUID = 5105914722614237201L;

 @ManyToOne
 protected Message msg;

 @ManyToOne
 protected Account recipient;

 private boolean newMessage;

 public Message getMsg() {
 return msg;
 }

 public void setMsg(Message msg) {
 this.msg = msg;
 }

 public Account getRecipient() {
 return recipient;
 }

 public void setRecipient(Account recipient) {
 this.recipient = recipient;
 }

 public boolean isNewMessage() {
 return newMessage;
 }

 public void setNewMessage(boolean newMessage) {
 this.newMessage = newMessage;
 }

 }