package net.pd.ethraa.common.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.integration.jackson.Views;

/**
 * Join Table between {@link Training} , {@link Day} entity
 *
 * @author Emad
 *
 */
@Entity
public class TrainingDay extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 897183910514239025L;

	@ManyToOne
	@NotNull
	@JsonView(Views.Details.class)
	private Day day;

	@ManyToOne
	@JsonIgnore
	@NotNull
	private Training training;

	@NotNull
	@NotEmpty
	@JsonView(Views.Details.class)
	private String startHour;

	@NotNull
	@NotEmpty
	@JsonView(Views.Details.class)
	private String endHour;

	@OneToMany(mappedBy = "trainingDay", cascade = CascadeType.ALL)
	private Set<TrainingAttendence> attendence = new HashSet<>();

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public Set<TrainingAttendence> getAttendence() {
		return attendence;
	}

	public void setAttendence(Set<TrainingAttendence> attendence) {
		this.attendence = attendence;
	}

}