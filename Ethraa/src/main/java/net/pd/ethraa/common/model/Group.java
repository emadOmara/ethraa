package net.pd.ethraa.common.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GROUPS")
public class Group extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 5852390428319467002L;

    private String name;
    private String description;

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

}
