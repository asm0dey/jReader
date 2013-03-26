package com.github.asm0dey.shared.domain;

import org.hibernate.annotations.Index;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * User: finkel
 * <p/>
 * Date: 25.03.13
 * <p/>
 * Time: 20:40
 */
@Entity
@Table( name = "human_feeditem" ,uniqueConstraints = {
        @UniqueConstraint(columnNames = {"human_id","feeditem_id"},name = "hfi_unik")
})
public class HumanFeedItem extends AbstractPojo {
	@ManyToOne
	@Index( name = "hfi_h_ind" )
	private Human human;
	@ManyToOne
	@Index( name = "hfi_f_ind" )
	private FeedItem feedItem;
	@Index( name = "hfi_r_ind" )
	private boolean isRead;
	@Index( name = "hfi_s_ind" )
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
