package com.github.asm0dey.shared.domain;

import com.google.common.collect.Lists;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Human: finkel
 * <p/>
 * Date: 15.03.13
 * <p/>
 * Time: 11:13
 */
@javax.persistence.Entity
@Table( uniqueConstraints = { @UniqueConstraint( columnNames = { "email" } ) } )
public class Human extends AbstractPojo {
	@Column( nullable = false )
	private String email;
	@Column( nullable = false )
	private String passwordHash;
	@Column( nullable = false )
	private boolean isActive;
	private int loginAttempts;
	@OneToMany( orphanRemoval = true, mappedBy = "owner" )
	@Column( nullable = false )
	@Cascade( value = { CascadeType.SAVE_UPDATE } )
	private List<FeedGroup> categories;
	@ManyToMany
	private List<FeedItem> readItems;
	@ManyToMany
	private List<FeedItem> starredItems;

	public Human( String email, String passwordHash ) {
		this( email, passwordHash, newArrayList( new FeedGroup( Lists.<Feed> newArrayList(), "Default" ) ) );
	}

	public Human( String email, String passwordHash, List<FeedGroup> categories ) {
		this( email, passwordHash, true, categories );
	}

	public Human( String email, String passwordHash, boolean active, List<FeedGroup> categories ) {
		this( email, passwordHash, active, 0, categories );
	}

	public Human( String email, String passwordHash, boolean active, int loginAttempts, List<FeedGroup> categories ) {
		this.email = email;
		this.passwordHash = passwordHash;
		isActive = active;
		this.loginAttempts = loginAttempts;
		this.categories = categories;
	}

	public Human() {
	}

	public String getEmail() {
		return email;
	}

	public Human setEmail( String email ) {
		this.email = email;
		return this;
	}

	public boolean isActive() {
		return isActive;
	}

	public Human setActive( boolean active ) {
		isActive = active;
		return this;
	}

	public int getLoginAttempts() {
		return loginAttempts;
	}

	public Human setLoginAttempts( int loginAttempts ) {
		this.loginAttempts = loginAttempts;
		return this;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public Human setPasswordHash( String passwordHash ) {
		this.passwordHash = passwordHash;
		return this;
	}

	public List<FeedGroup> getCategories() {
		return categories;
	}

	public Human setCategories( List<FeedGroup> subscribedFeeds ) {
		this.categories = subscribedFeeds;
		return this;
	}

	public List<FeedItem> getReadItems() {
		return readItems;
	}

	public void setReadItems( List<FeedItem> readItems ) {
		this.readItems = readItems;
	}

	public List<FeedItem> getStarredItems() {
		return starredItems;
	}

	public void setStarredItems( List<FeedItem> starredItems ) {
		this.starredItems = starredItems;
	}
}
