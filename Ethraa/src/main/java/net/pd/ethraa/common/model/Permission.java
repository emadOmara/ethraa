package net.pd.ethraa.common.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.integration.jackson.Views;

@Entity
@Table(name = "PERMISSION")
public class Permission extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 4635122625047041490L;

    @JsonView(Views.LoginSuccess.class)
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
