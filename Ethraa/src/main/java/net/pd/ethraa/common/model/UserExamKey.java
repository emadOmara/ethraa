package net.pd.ethraa.common.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class UserExamKey implements Serializable {

	/**
	*
	*/
	private static final long serialVersionUID = -4002033881064538717L;

	@ManyToOne
	private Account account;

	@ManyToOne
	private Exam exam;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((exam == null) ? 0 : exam.hashCode());
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
		UserExamKey other = (UserExamKey) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (exam == null) {
			if (other.exam != null)
				return false;
		} else if (!exam.equals(other.exam))
			return false;
		return true;
	}

}