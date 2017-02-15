package net.pd.ethraa.common.model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

/***
 * Exam entity**
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
	@JsonIgnore
	private UserExam userExam;

	@ManyToOne
	private Question question;

	@ManyToOne
	private Answer answer;

	@Lob
	private String writtenAnswer;

	private Long score;

	public UserExam getUserExam() {
		return userExam;
	}

	public void setUserExam(UserExam userExam) {
		this.userExam = userExam;
	}

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

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

}
