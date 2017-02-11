package net.pd.ethraa.common.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

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

	@OneToMany(mappedBy = "userExam")
	private List<Solution> solutions;

	public List<Solution> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<Solution> solutions) {
		this.solutions = solutions;
	}

}
