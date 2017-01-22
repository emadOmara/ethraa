package net.pd.ethraa.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import net.pd.ethraa.common.CommonUtil;

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

    private String title;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<TrainingDay> trainingDays;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private Double latitude;
    private Double longitude;

    private String address;

    @ManyToMany
    private Set<Group> groups;

    private int points;

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

    public int getPoints() {
	return points;
    }

    public void setPoints(int points) {
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

    public static void main(String[] args) throws JsonProcessingException {
	Training n = new Training();
	n.setAddress("Cairo");
	n.setEndDate(new Date());
	n.setStartDate(new Date());
	Group g = new Group();
	g.setId(2l);
	Set<Group> groups = new HashSet<>();
	groups.add(g);
	n.setGroups(groups);

	n.setLatitude(6513513d);
	n.setLongitude(351535131d);

	n.setPoints(50);
	n.setTitle("Programming course");

	TrainingDay entry = new TrainingDay();

	Day d = new Day();
	d.setId(1l);
	entry.setDay(d);

	entry.setEndHour("5:30");
	entry.setStartHour("1:30");
	// entry.setTraining(n);

	List<TrainingDay> days = new ArrayList<>();

	days.add(entry);

	n.setTrainingDays(days);

	ObjectMapper mapper = new ObjectMapper();
	mapper.enable(SerializationFeature.INDENT_OUTPUT);

	System.out.println(mapper.writeValueAsString(n));

    }
}