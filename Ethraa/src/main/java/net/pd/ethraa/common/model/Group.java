package net.pd.ethraa.common.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.integration.jackson.Views;

@Entity
@Table(name = "GROUPS")
public class Group extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 5852390428319467002L;

	@JsonView({ Views.Public.class, Views.Group.class })
	@Column(unique = true)
	private String name;
	private String description;
	@Transient
	@JsonView(Views.Group.class)
	private Long pendingRequests;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Message> messages = new ArrayList<>();

	@ManyToMany(mappedBy = "groups", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Book> books = new ArrayList<>();

	@ManyToMany(mappedBy = "groups", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Exam> exams = new ArrayList<>();

	@ManyToMany(mappedBy = "groups", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Training> trainings = new ArrayList<>();

	public Group() {
	}

	public Group(Long groupID) {
		id = groupID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getPendingRequests() {
		return pendingRequests;
	}

	public void setPendingRequests(Long pendingRequests) {
		this.pendingRequests = pendingRequests;
	}

}
