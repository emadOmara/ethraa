package net.pd.ethraa.common.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Join Table between {@link Training} , {@link Day} entity
 *
 * @author Emad
 *
 */
@Entity
public class TrainingDays extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 897183910514239025L;

    @ManyToOne
    private Day day;

    @ManyToOne
    @JsonIgnore
    private Training training;

    private String startHour;

    private String endHour;

    @ManyToMany
    private Set<Account> accounts = new HashSet<>();

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

    public Set<Account> getAccounts() {
	return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
	this.accounts = accounts;
    }

}