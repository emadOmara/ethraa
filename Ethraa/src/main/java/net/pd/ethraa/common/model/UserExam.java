package net.pd.ethraa.common.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/***Exam entity**

@author Emad
 *
 */
 @Entity
 public class UserExam implements Serializable {

 /**
 *
 */
 private static final long serialVersionUID = -1732232204178493231L;

 @ManyToOne
 @Id
 private Account account;

 @ManyToOne
 @Id
 private Exam exam;

 @OneToMany(mappedBy = "userExam")
 private List<Solution> solutions;

 public Account getAccount() {
 return account;
 }

 public void setAccount(Account account) {
 this.account = account;
 }

 public Exam getExam() {
 return exam;
 }

 public void setExam(Exam exam) {
 this.exam = exam;
 }

 public List<Solution> getSolutions() {
 return solutions;
 }

 public void setSolutions(List<Solution> solutions) {
 this.solutions = solutions;
 }

 }
