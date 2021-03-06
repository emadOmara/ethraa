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

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	@JsonIgnore
	private List<Message> messages;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "recipient")
	@JsonIgnore
	private List<MessageRecipients> messageRecipients;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "account")
	@JsonIgnore
	private List<TrainingAttendence> trainingAttendences;

	@ManyToMany(mappedBy = "accounts", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Book> books = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "id.account")
	@JsonIgnore
	private List<UserExam> userExams;

	@Transient
	@JsonView(Views.Details.class)
	private Long totalTrainingDays;

	@Transient
	@JsonView(Views.ExamPublic.class)
	private Long examFullMark;

	@Transient
	@JsonView(Views.ExamPublic.class)
	private Long examScore;
	@Transient
	@JsonView(Views.ExamPublic.class)
	private Long examStatus;
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

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
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

	public Long getExamStatus() {
		return examStatus;
	}

	public void setExamStatus(Long examStatus) {
		this.examStatus = examStatus;
	}

}
