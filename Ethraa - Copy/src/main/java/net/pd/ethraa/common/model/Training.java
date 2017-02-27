package net.pd.ethraa.common.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.common.CommonUtil;
import net.pd.ethraa.integration.jackson.Views;

/**
 * Training entity
 *
 * @author Emad
 *
 */
@Entity
public class Training extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 218670096638085713L;

    @JsonView(Views.Public.class)
    private String title;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    @JsonView(Views.Details.class)
    private List<TrainingDay> trainingDays;

    @JsonView(Views.Public.class)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @JsonView(Views.Public.class)
    private Long type;

    @JsonView(Views.Public.class)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @JsonView(Views.Public.class)
    private Double latitude;
    @JsonView(Views.Public.class)
    private Double longitude;

    @JsonView(Views.Public.class)
    private String address;

    @ManyToMany
    @JsonView(Views.Details.class)
    private Set<Group> groups;

    @JsonView(Views.Public.class)
    private Long points;

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public Date getStartDate() {
	return startDate;
    }

    public void setStartDate(Date startDate) {
	this.startDate = startDate;
    }

    public Date getEndDate() {
	return endDate;
    }

    public void setEndDate(Date endDate) {
	this.endDate = endDate;
    }

    public Double getLatitude() {
	return latitude;
    }

    public void setLatitude(Double latitude) {
	this.latitude = latitude;
    }

    public Double getLongitude() {
	return longitude;
    }

    public void setLongitude(Double longitude) {
	this.longitude = longitude;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public Set<Group> getGroups() {
	return groups;
    }

    public void setGroups(Set<Group> groups) {
	this.groups = groups;
    }

    public Long getPoints() {
	return points;
    }

    public void setPoints(Long points) {
	this.points = points;
    }

    public List<TrainingDay> getTrainingDays() {
	return trainingDays;
    }

    public void setTrainingDays(List<TrainingDay> trainingDays) {
	this.trainingDays = trainingDays;
	if (!CommonUtil.isEmpty(this.trainingDays)) {
	    for (TrainingDay entry : this.trainingDays) {
		entry.setTraining(this);
	    }
	}
    }

    public Long getType() {
	return type;
    }

    public void setType(Long type) {
	this.type = type;
    }

}