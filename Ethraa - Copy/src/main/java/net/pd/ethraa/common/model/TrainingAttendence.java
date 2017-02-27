package net.pd.ethraa.common.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Join Table between {@link TrainingDay} , {@link Account} entity
 *
 * @author Emad
 *
 */
@Entity
public class TrainingAttendence extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 897183910514239025L;

	@ManyToOne
	@NotNull
	private TrainingDay trainingDay;

	@ManyToOne
	@NotNull
	private Account account;

	@Temporal(TemporalType.DATE)
	private Date attendenceDate = Calendar.getInstance().getTime();

	public TrainingDay getTrainingDay() {
		return trainingDay;
	}

	public void setTrainingDay(TrainingDay trainingDay) {
		this.trainingDay = trainingDay;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getAttendenceDate() {
		return attendenceDate;
	}

	public void setAttendenceDate(Date attendenceDate) {
		this.attendenceDate = attendenceDate;
	}

}