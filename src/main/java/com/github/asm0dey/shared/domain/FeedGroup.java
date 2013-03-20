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
	@ManyToMany
	@Cascade( CascadeType.SAVE_UPDATE )
	private List<Feed> feeds;
	@Column( nullable = false )
	private String name;
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

	public FeedGroup( List<Feed> feeds, String name ) {
		this.feeds = feeds;
		this.name = name;
	}

	public FeedGroup() {
	}

}
