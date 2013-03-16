package com.github.asm0dey.shared.domain;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Collection;
import java.util.Date;

/**
 * Human: finkel
 * <p/>
 * Date: 15.03.13
 * <p/>
 * Time: 11:17
 */

@javax.persistence.Entity
@Table( uniqueConstraints = { @UniqueConstraint( columnNames = { "url" } ) } )
public class Feed extends AbstractPojo {
	@Column( nullable = false )
	private String url;
	private Date lastUpdateDate;
	@ManyToMany
	private Collection<FeedGroup> feedGroups;

	public Feed() {
	}

	public Feed( Date lastUpdateDate, String url ) {
		this.lastUpdateDate = lastUpdateDate;
		this.url = url;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate( Date lastUpdateDate ) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl( String url ) {
		this.url = url;
	}

	public Collection<FeedGroup> getFeedGroups() {
		return feedGroups;
	}

	public void setFeedGroups( Collection<FeedGroup> feedGroups ) {
		this.feedGroups = feedGroups;
	}

	@Override
	public String toString() {
		return "Feed{" + "feedGroups=" + feedGroups + ", url='" + url + '\'' + ", lastUpdateDate=" + lastUpdateDate + ", id=" + getId() + '}';
	}
}
