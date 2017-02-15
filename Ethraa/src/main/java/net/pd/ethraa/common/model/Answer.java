package net.pd.ethraa.common.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.integration.jackson.Views;

/**
 * Answer entity
 *
 * @author Emad
 *
 */
@Entity
public class Answer extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 2632578855910228130L;

	@NotEmpty
	@JsonView(Views.Public.class)
	private String answer;

	@ManyToOne
	@JsonIgnore
	private Question question;

	@JsonView(Views.Public.class)
	private boolean correct;

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

}
