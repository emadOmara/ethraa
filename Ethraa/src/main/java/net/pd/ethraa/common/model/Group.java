package net.pd.ethraa.common.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "GROUPS")
// @JsonDeserialize(using = GroupDeserializer.class)
public class Group extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 5852390428319467002L;

    private String name;
    private String description;

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

    @JsonIgnore
    public String getDescription() {
	return description;
    }

    @JsonProperty
    public void setDescription(String description) {
	this.description = description;
    }

}
