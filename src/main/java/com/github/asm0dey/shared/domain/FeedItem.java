package com.github.asm0dey.shared.domain;

import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 16:49
 */
@Entity
public class FeedItem extends AbstractPojo {
	@Column( length = 2000 )
	@Index( name = "item_title_index" )
	private String title;
	@Column(length = 32000)
	private String text;
	@Index( name = "item_date_index" )
	private Date createdOn;
	private String author;
	@Index( name = "item_url" )
	private String url;

	public FeedItem( Date createdOn, String text, String title, String url ) {
		this.createdOn = createdOn;
		this.text = text;
		this.title = title;
		this.url = url;
	}

	public FeedItem() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText( String text ) {
		this.text = text;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn( Date createdOn ) {
		this.createdOn = createdOn;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl( String url ) {
		this.url = url;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor( String author ) {
		this.author = author;
	}
    @Index(name = "fi_feed_index")
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private Feed feed;

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
}
