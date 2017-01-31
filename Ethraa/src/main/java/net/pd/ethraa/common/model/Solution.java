package net.pd.ethraa.common.model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * Exam entity
 *
 * @author Emad
 *
 */
@Entity
public class Solution extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = -1732232204178493231L;

    @ManyToOne
    private UserExam userExam;

    @ManyToOne
    private Question question;

    @ManyToOne
    private Answer answer;

    @Lob
    private String writtenAnswer;

    // public UserExam getUserExam() {
    // return userExam;
    // }
    //
    // public void setUserExam(UserExam userExam) {
    // this.userExam = userExam;
    // }

    public Question getQuestion() {
	return question;
    }

    public void setQuestion(Question question) {
	this.question = question;
    }

    public Answer getAnswer() {
	return answer;
    }

    public void setAnswer(Answer answer) {
	this.answer = answer;
    }

    public String getWrittenAnswer() {
	return writtenAnswer;
    }

    public void setWrittenAnswer(String writtenAnswer) {
	this.writtenAnswer = writtenAnswer;
    }

}
