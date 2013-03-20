package com.github.asm0dey.shared.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;

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
	@OneToMany(mappedBy = "feed",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<FeedItem> items;
	private String title;
	private String charset;
	private String imageUrl;

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

/*	public Collection<FeedGroup> getFeedGroups() {
		return feedGroups;
	}

	public void setFeedGroups( Collection<FeedGroup> feedGroups ) {
		this.feedGroups = feedGroups;
	}

	@Override
	public String toString() {
		return "Feed{" + "feedGroups=" + feedGroups + ", url='" + url + '\'' + ", lastUpdateDate=" + lastUpdateDate + ", id=" + getId() + '}';
	}*/

	public Set<FeedItem> getItems() {
		return items;
	}

	public void setItems( Set<FeedItem> items ) {
		this.items = items;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset( String charset ) {
		this.charset = charset;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl( String imageUrl ) {
		this.imageUrl = imageUrl;
	}
}
