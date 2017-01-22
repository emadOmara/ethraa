package net.pd.ethraa.common.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * account Points entity
 *
 * @author Emad
 *
 */
@Entity
public class Point extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = -2686273813253724965L;

    private Long points;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Account account;

    private int type;

    private Long entityId;

    public Long getPoints() {
	return points;
    }

    public void setPoints(Long points) {
	this.points = points;
    }

    public Account getAccount() {
	return account;
    }

    public void setAccount(Account account) {
	this.account = account;
    }

    public int getType() {
	return type;
    }

    public void setType(int type) {
	this.type = type;
    }

    public Long getEntityId() {
	return entityId;
    }

    public void setEntityId(Long entityId) {
	this.entityId = entityId;
    }

}