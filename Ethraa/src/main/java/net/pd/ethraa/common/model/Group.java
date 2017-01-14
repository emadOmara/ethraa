package net.pd.ethraa.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

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
