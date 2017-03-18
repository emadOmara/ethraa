package net.pd.ethraa.common.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.integration.jackson.Views;

/**
 * Book entity
 *
 * @author Emad
 *
 */
@Entity
public class Book extends BaseEntity {

	private static final long serialVersionUID = 5105914722614237201L;

	/**
	 *
	 */

	@JsonView(Views.Public.class)
	private String bookName;

	@JsonView(Views.Public.class)
	private String publisher;

	@JsonView(Views.Public.class)
	private String author;

	@JsonView(Views.Public.class)
	private String url;

	@JsonView(Views.Public.class)
	private Integer points = 0;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@JsonView(Views.Details.class)
	private String image;

	@Transient
	@JsonView(Views.Public.class)
	private boolean read;

	@ManyToMany
	@JsonView(Views.Details.class)
	private List<Group> groups;

	@ManyToMany(/* cascade = CascadeType.ALL */)
	// @JoinTable(name = "BOOK_READERS")

	@JoinTable(name = "BOOK_READERS", joinColumns = {
			@JoinColumn(name = "book_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "accounts_id", referencedColumnName = "id") })
	private Set<Account> accounts = new HashSet<>();

	@JsonView(Views.Public.class)
	@Transient
	Long actualReaders;
	@JsonView(Views.Public.class)
	@Transient
	Long missingReaders;

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Long getActualReaders() {
		return actualReaders;
	}

	public void setActualReaders(Long actualReaders) {
		this.actualReaders = actualReaders;
	}

	public Long getMissingReaders() {
		return missingReaders;
	}

	public void setMissingReaders(Long missingReaders) {
		this.missingReaders = missingReaders;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

}