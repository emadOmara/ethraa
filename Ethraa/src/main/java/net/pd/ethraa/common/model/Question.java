package net.pd.ethraa.common.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.pd.ethraa.common.CommonUtil;

/**
 * Exam entity
 *
 * @author Emad
 *
 */
@Entity
public class Question extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1606984488816853694L;

	@NotEmpty
	private String question;

	private Integer score;

	private Integer type;

	@ManyToOne
	@JsonIgnore
	private Exam exam;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Answer> answers = new HashSet<>();

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
		if (!CommonUtil.isEmpty(this.answers)) {
			for (Answer answer : answers) {
				answer.setQuestion(this);
			}
		}
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
