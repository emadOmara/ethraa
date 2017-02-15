package net.pd.ethraa.common.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
	private Set<Question> questions = new HashSet<>();

	@ManyToOne
	private Training training;

	@Transient
	@JsonView(Views.ExamPublic.class)
	private Long examFullMark;

	@Transient
	@JsonView(Views.ExamPublic.class)
	private Long examScore;

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

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
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

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
		if (!CommonUtil.isEmpty(this.questions)) {
			for (Question question : questions) {
				question.setExam(this);
			}
		}
	}
	// public static void main(String[] args) throws JsonProcessingException {
	// Exam e = new Exam();
	//
	// e.setId(1l);
	// e.setTitle("c++");
	// Training t = new Training();
	// t.setId(1l);
	// e.setTraining(t);
	//
	// Group g = new Group();
	// g.setId(3l);
	//
	// List<Group> groups = new ArrayList<>();
	// groups.add(g);
	// e.setGroups(groups);
	//
	// Question q = new Question();
	// q.setId(4l);
	// q.setQuestion("how r u ?");
	// q.setExam(e);
	// q.setScore(100);
	// q.setType(EthraaConstants.EXAM_QUESTION_TYPE_MC);
	//
	// Answer ans = new Answer();
	// ans.setAnswer("one");
	// ans.setCorrect(true);
	// ans.setId(4l);
	// ans.setQuestion(q);
	//
	// Set<Answer> answers = new HashSet<>();
	// answers.add(ans);
	// q.setAnswers(answers);
	//
	// Set<Question> questions = new HashSet<>();
	// questions.add(q);
	// e.setQuestions(questions);
	//
	// ObjectMapper mapper = new ObjectMapper();
	// String result = mapper.writeValueAsString(e);
	// System.out.println(result);
	//
	// }
}
