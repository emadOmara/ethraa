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

    private Long type;

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

    public Long getEntityId() {
	return entityId;
    }

    public void setEntityId(Long entityId) {
	this.entityId = entityId;
    }

    public Long getType() {
	return type;
    }

    public void setType(Long type) {
	this.type = type;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = prime;
	result = prime * result + (account == null ? 0 : account.hashCode());
	result = prime * result + (entityId == null ? 0 : entityId.hashCode());
	result = prime * result + (type == null ? 0 : type.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	// No super call to exclude the id from comparison
	// if (!super.equals(obj)) {
	// return false;
	// }
	if (getClass() != obj.getClass()) {
	    return false;
	}
	Point other = (Point) obj;
	if (account == null) {
	    if (other.account != null) {
		return false;
	    }
	} else if (!account.equals(other.account)) {
	    return false;
	}
	if (entityId == null) {
	    if (other.entityId != null) {
		return false;
	    }
	} else if (!entityId.equals(other.entityId)) {
	    return false;
	}
	if (type != other.type) {
	    return false;
	}
	return true;
    }

}