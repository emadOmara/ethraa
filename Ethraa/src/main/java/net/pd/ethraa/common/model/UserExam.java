package net.pd.ethraa.common.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.codehaus.jackson.map.annotate.JsonView;

import net.pd.ethraa.common.CommonUtil;
import net.pd.ethraa.integration.jackson.Views;

/***
 * Exam entity**
 * 
 * @author Emad
 *
 */
@Entity
public class UserExam implements Serializable {

	/**
	*
	*/
	private static final long serialVersionUID = -1732232204178493231L;

	@EmbeddedId
	private UserExamKey id;

	@JsonView(Views.Public.class)
	private Long status = 0l;

	@OneToMany(mappedBy = "userExam", cascade = CascadeType.ALL, orphanRemoval = true)
	// @JoinColumns(value = { @JoinColumn(name = "account"), @JoinColumn(name =
	// "exam") })
	private List<Solution> solutions;

	public List<Solution> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<Solution> solutions) {
		this.solutions = solutions;
		if (!CommonUtil.isEmpty(this.solutions)) {
			for (Solution solution : solutions) {
				solution.setUserExam(this);
			}
		}
	}

	public UserExamKey getId() {
		return id;
	}

	public void setId(UserExamKey id) {
		this.id = id;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserExam other = (UserExam) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	// public static void main(String[] args) throws JsonProcessingException {
	// UserExam uExam = new UserExam();
	//
	// UserExamKey key = new UserExamKey();
	//
	// Account account = new Account();
	// account.setId(1l);
	//
	// Exam exam = new Exam();
	// exam.setId(4l);
	// key.setAccount(account);
	// key.setExam(exam);
	// uExam.setId(key);
	//
	// List<Solution> solutions = new ArrayList<>();
	// Solution s = new Solution();
	//
	// Question question = new Question();
	// question.setId(2l);
	// question.setQuestion("what is your opinion");
	//
	// Answer ans = new Answer();
	// ans.setId(2l);
	// ans.setAnswer("no thing");
	//
	// s.setAnswer(ans);
	// s.setQuestion(question);
	// s.setUserExam(uExam);
	// solutions.add(s);
	// s = new Solution();
	//
	// s.setQuestion(question);
	// s.setWrittenAnswer("this is anything desc");
	// s.setUserExam(uExam);
	// solutions.add(s);
	// uExam.setSolutions(solutions);
	//
	// ObjectMapper mapper = new ObjectMapper();
	// String val = mapper.writeValueAsString(uExam);
	// System.out.println(val);
	// }
}
