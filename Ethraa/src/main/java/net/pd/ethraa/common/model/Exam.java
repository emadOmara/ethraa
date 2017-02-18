package net.pd.ethraa.common.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.common.CommonUtil;
import net.pd.ethraa.integration.jackson.Views;

/**
 * Exam entity
 *
 * @author Emad
 *
 */
@Entity
public class Exam extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1606984488816853694L;

	@JsonView(Views.Public.class)
	private String title;

	@ManyToMany
	private List<Group> groups;

	@OneToMany(mappedBy = "id.exam", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserExam> userExams = new HashSet<>();

	@OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonView(Views.Public.class)
	private List<Question> questions = new ArrayList<Question>();

	@Transient
	@JsonView(Views.ExamPublic.class)
	private Long examFullMark;

	@Transient
	@JsonView(Views.ExamPublic.class)
	private Long examScore;

	@Transient
	@JsonView(Views.ExamPublic.class)
	private Long examUserSolutions;
	@Transient
	@JsonView(Views.ExamPublic.class)
	private Long totalExamMembers;

	@Transient
	@JsonView(Views.ExamPublic.class)
	private Long examAdminEvaluations;

	@Transient
	@JsonView(Views.ExamPublic.class)
	private Long examStatus;

	@JsonView(Views.Public.class)
	private Long type;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Set<UserExam> getUserExams() {
		return userExams;
	}

	public void setUserExams(Set<UserExam> userExams) {
		this.userExams = userExams;
	}

	public Long getExamFullMark() {
		return examFullMark;
	}

	public void setExamFullMark(Long examFullMark) {
		this.examFullMark = examFullMark;
	}

	public Long getExamScore() {
		return examScore;
	}

	public void setExamScore(Long examScore) {
		this.examScore = examScore;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
		if (!CommonUtil.isEmpty(this.questions)) {
			for (Question question : questions) {
				question.setExam(this);
			}
		}
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getExamStatus() {
		return examStatus;
	}

	public void setExamStatus(Long examStatus) {
		this.examStatus = examStatus;
	}

	public Long getExamUserSolutions() {
		return examUserSolutions;
	}

	public void setExamUserSolutions(Long examUserSolutions) {
		this.examUserSolutions = examUserSolutions;
	}

	public Long getExamAdminEvaluations() {
		return examAdminEvaluations;
	}

	public void setExamAdminEvaluations(Long examAdminEvaluations) {
		this.examAdminEvaluations = examAdminEvaluations;
	}

	public Long getTotalExamMembers() {
		return totalExamMembers;
	}

	public void setTotalExamMembers(Long totalExamMembers) {
		this.totalExamMembers = totalExamMembers;
	}

}
