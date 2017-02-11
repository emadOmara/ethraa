package net.pd.ethraa.common.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.integration.jackson.Views;

/**
 * Account entity
 *
 * @author Emad
 *
 */
@Entity
@Table(name = "ACCOUNT", uniqueConstraints = @UniqueConstraint(columnNames = { "mobile", "email" }) )
public class Account extends BaseEntity {

	/**
	 *
	 */

	private static final long serialVersionUID = 5105914722614237201L;

	@NotEmpty
	@JsonView(Views.Public.class)
	private String userName;
	@NotEmpty
	@Column(unique = true)
	private String mobile;
	@Email
	@Column(unique = true)
	private String email;
	@NotEmpty
	private String password;
	@Min(1950)
	private Integer graduationYear;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "sender")
	private List<Message> messages;

	@Transient
	@JsonView(Views.Details.class)
	private Long totalTrainingDays;

	@Transient
	@JsonView(Views.Details.class)
	private Long trainingPoints = 0l;

	@Transient
	@JsonView(Views.Details.class)
	private Long trainingAttendence;

	@Transient
	@JsonView(Views.Details.class)
	private boolean attendedTrainingToday;

	@Enumerated(EnumType.STRING)
	@JsonView(Views.LoginSuccess.class)
	private AccountType accountType;

	@JsonView(Views.Public.class)
	private Integer accountStatus;

	@ManyToOne
	@JsonView(Views.LoginSuccess.class)
	private Group group;

	@JsonView(Views.LoginSuccess.class)
	@ManyToMany
	@JoinTable(name = "ACCOUNT_PERMISSION")
	private List<Permission> permissions;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private Set<Point> points = new HashSet<>();

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getGraduationYear() {
		return graduationYear;
	}

	public void setGraduationYear(Integer graduationYear) {
		this.graduationYear = graduationYear;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Set<Point> getPoints() {
		return points;
	}

	public void setPoints(Set<Point> points) {
		this.points = points;
	}

	public Long getTotalTrainingDays() {
		return totalTrainingDays;
	}

	public void setTotalTrainingDays(Long totalTrainingDays) {
		this.totalTrainingDays = totalTrainingDays;
	}

	public Long getTrainingAttendence() {
		return trainingAttendence;
	}

	public void setTrainingAttendence(Long trainingAttendence) {
		this.trainingAttendence = trainingAttendence;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public boolean isAttendedTrainingToday() {
		return attendedTrainingToday;
	}

	public void setAttendedTrainingToday(boolean attendedTrainingToday) {
		this.attendedTrainingToday = attendedTrainingToday;
	}

	public Long getTrainingPoints() {
		return trainingPoints;
	}

	public void setTrainingPoints(Long trainingPoints) {
		this.trainingPoints = trainingPoints;
	}

}
