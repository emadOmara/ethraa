package net.pd.ethraa.common.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * account Points entity
 *
 * @author Emad
 *
 */
@Entity
public class Points extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = -2686273813253724965L;

    private int points;

    @ManyToOne
    private Account account;

    private int type;

    public int getPoints() {
	return points;
    }

    public void setPoints(int points) {
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

}