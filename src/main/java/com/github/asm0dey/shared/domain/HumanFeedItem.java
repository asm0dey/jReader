package com.github.asm0dey.shared.domain;

import javax.persistence.*;

/**
 * User: finkel
 * <p/>
 * Date: 25.03.13
 * <p/>
 * Time: 20:40
 */
@Entity
@Table( name = "human_feeditem")
public class HumanFeedItem extends AbstractPojo {
	@ManyToOne
	private Human human;
	@ManyToOne
	private FeedItem feedItem;
	private boolean isRead;
	private boolean isStarred;

	public FeedItem getFeedItem() {
		return feedItem;
	}

	public void setFeedItem( FeedItem feedItem ) {
		this.feedItem = feedItem;
	}

	public Human getHuman() {
		return human;
	}

	public void setHuman( Human human ) {
		this.human = human;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead( boolean read ) {
		isRead = read;
	}

	public boolean isStarred() {
		return isStarred;
	}

	public void setStarred( boolean starred ) {
		isStarred = starred;
	}
}
