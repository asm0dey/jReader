package com.github.asm0dey.shared.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;

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
	@OneToMany( orphanRemoval = true/*, mappedBy = "owner"*/, fetch = FetchType.EAGER )
	@Column( nullable = false )
	@Cascade( value = { CascadeType.SAVE_UPDATE } )
// @MapKey(name = "name")
	private Map<String, FeedGroup> categories;


	public Human( String email, String passwordHash ) {
		this.email = email;
		this.passwordHash = passwordHash;
	}

	public Human( String email, String passwordHash, Map<String, FeedGroup> categories ) {
		this( email, passwordHash, true, categories );
	}

	public Human( String email, String passwordHash, boolean active, Map<String, FeedGroup> categories ) {
		this( email, passwordHash, active, 0, categories );
	}

	public Human( String email, String passwordHash, boolean active, int loginAttempts, Map<String, FeedGroup> categories ) {
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

	public Map<String, FeedGroup> getCategories() {
		return categories;
	}

	public void setCategories( Map<String, FeedGroup> categories ) {
		this.categories = categories;
	}
}
