package net.pd.ethraa.common.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.integration.jackson.Views;

/**
 * Day entity , Sun=1,Mon=2...etc
 *
 * @author Emad
 *
 */
@Entity
public class Day implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8013255246752663440L;

    @Id
    @JsonView(Views.Base.class)
    private Long id;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    private String name;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (id == null ? 0 : id.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	Day other = (Day) obj;
	if (id == null) {
	    if (other.id != null) {
		return false;
	    }
	} else if (!id.equals(other.id)) {
	    return false;
	}
	return true;
    }

}