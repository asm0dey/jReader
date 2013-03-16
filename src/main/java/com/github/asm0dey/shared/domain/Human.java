package com.github.asm0dey.shared.domain;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

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
	@OneToMany( targetEntity = FeedGroup.class, mappedBy = "human" )
	@Column( nullable = false )
	private List<FeedGroup> subscribedFeeds;

	public Human( String email, String passwordHash, boolean active, int loginAttempts, List<FeedGroup> subscribedFeeds ) {
		this.email = email;
		this.passwordHash = passwordHash;
		isActive = active;
		this.loginAttempts = loginAttempts;
		this.subscribedFeeds = subscribedFeeds;
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

	public List<FeedGroup> getSubscribedFeeds() {
		return subscribedFeeds;
	}

	public Human setSubscribedFeeds( List<FeedGroup> subscribedFeeds ) {
		this.subscribedFeeds = subscribedFeeds;
		return this;
	}

	@Override
	public String toString() {
		return "Human{" + "email='" + email + '\'' + ", passwordHash='" + passwordHash + '\'' + ", isActive=" + isActive + ", loginAttempts="
				+ loginAttempts + ", subscribedFeeds=" + subscribedFeeds + '}';
	}
}
