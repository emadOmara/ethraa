package net.pd.ethraa.common.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Account entity
 *
 * @author Emad
 *
 */
@Entity
@Table(name = "ACCOUNT", uniqueConstraints = @UniqueConstraint(columnNames = { "mobile", "email" }))
public class Account extends BaseEntity {

    /**
     *
     */

    private static final long serialVersionUID = 5105914722614237201L;

    @NotEmpty
    private String userName;
    @NotEmpty
    @Column(unique = true)
    private String mobile;
    @Email
    @Column(unique = true)
    private String email;
    @NotEmpty
    private String password;
    private int graduationYear;

    @Enumerated(EnumType.STRING)
    private AccountType accountType = AccountType.NORMAL;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.INACTIVE;

    @ManyToOne
    private Group group;

    @ManyToMany
    @JoinTable(name = "ACCOUNT_PERMISSION")
    private List<Permission> permissions;

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

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public List<Permission> getPermissions() {
	return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
	this.permissions = permissions;
    }

    public AccountType getAccountType() {
	return accountType;
    }

    public void setAccountType(AccountType accountType) {
	this.accountType = accountType;
    }

    public AccountStatus getAccountStatus() {
	return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
	this.accountStatus = accountStatus;
    }

}