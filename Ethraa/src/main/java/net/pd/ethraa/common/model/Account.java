package net.pd.ethraa.common.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "ACCOUNT")
public class Account extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 5105914722614237201L;

    @NotEmpty
    private String username;
    @NotEmpty
    private String mobile;
    @Email
    private String email;
    @NotEmpty
    private String password;
    private String token;
    private int graduationYear;

    @ManyToOne
    private Group group;

    @ManyToMany
    @JoinTable(name = "ACCOUNT_PERMISSION")
    private List<Permission> permissions;

    @Temporal(TemporalType.DATE)
    private Date tokenExpiryDate;

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getMobile() {
	return mobile;
    }

    public void setMobile(String mobile) {
	this.mobile = mobile;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getToken() {
	return token;
    }

    public void setToken(String token) {
	this.token = token;
    }

    public int getGraduationYear() {
	return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
	this.graduationYear = graduationYear;
    }

    public Group getGroup() {
	return group;
    }

    public void setGroup(Group group) {
	this.group = group;
    }

    public Date getTokenExpiryDate() {
	return tokenExpiryDate;
    }

    public void setTokenExpiryDate(Date tokenExpiryDate) {
	this.tokenExpiryDate = tokenExpiryDate;
    }

}