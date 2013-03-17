package com.github.asm0dey.shared.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
	@OneToMany( mappedBy = "feed", targetEntity = FeedItem.class )
	private List<FeedItem> items;

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

	public List<FeedItem> getItems() {
		return items;
	}

	public void setItems( List<FeedItem> items ) {
		this.items = items;
	}

	@Override
	public boolean equals( Object o ) {
		if ( this == o )
			return true;
		if ( !( o instanceof Feed ) )
			return false;
		if ( !super.equals( o ) )
			return false;

		Feed feed = (Feed) o;

		if ( feedGroups != null ? !feedGroups.equals( feed.feedGroups ) : feed.feedGroups != null )
			return false;
		if ( !items.equals( feed.items ) )
			return false;
		if ( lastUpdateDate != null ? !lastUpdateDate.equals( feed.lastUpdateDate ) : feed.lastUpdateDate != null )
			return false;
		if ( !url.equals( feed.url ) )
			return false;

		return true;
	}

	@Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (feedGroups != null ? feedGroups.hashCode() : 0);
        return result;
    }
}
