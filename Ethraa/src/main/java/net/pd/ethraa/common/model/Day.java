package net.pd.ethraa.common.model;

import javax.persistence.Entity;

/**
 * Day entity
 *
 * @author Emad
 *
 */
@Entity
public class Day extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 8013255246752663440L;
    private String name;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

}