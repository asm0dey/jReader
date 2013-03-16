package com.github.asm0dey.shared.domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Human: finkel
 * <p/>
 * Date: 15.03.13
 * <p/>
 * Time: 11:16
 */
@javax.persistence.Entity
@Table
public class FeedGroup extends AbstractPojo {
	@Column( nullable = false )
	@ManyToMany( targetEntity = Feed.class, mappedBy = "feedGroups" )
	@Cascade( CascadeType.SAVE_UPDATE )
	private List<Feed> feeds;
	@Column( nullable = false )
	private String name;
	@ManyToOne( optional = false )
	private Human human;

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public List<Feed> getFeeds() {
		return feeds;
	}

	public void setFeeds( List<Feed> feeds ) {
		this.feeds = feeds;
	}

	public Human getHuman() {
		return human;
	}

	public void setHuman( Human users ) {
		this.human = users;
	}

	@Override
	public boolean equals( Object o ) {
		if ( this == o )
			return true;
		if ( !( o instanceof FeedGroup ) )
			return false;

		FeedGroup feedGroup = (FeedGroup) o;

		if ( !feeds.equals( feedGroup.feeds ) )
			return false;
		if ( !human.equals( feedGroup.human ) )
			return false;
		if ( !name.equals( feedGroup.name ) )
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = feeds.hashCode();
		result = 31 * result + name.hashCode();
		result = 31 * result + human.hashCode();
		return result;
	}

	public FeedGroup( List<Feed> feeds, String name ) {
		this.feeds = feeds;
		this.name = name;
	}

	public FeedGroup() {
	}

}
