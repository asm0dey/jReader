package com.github.asm0dey.shared.domain;

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
	@Lob
    private String title;
    @Lob
	private String text;
	private Date createdOn;
	@ManyToOne( optional = false, fetch = FetchType.EAGER )
	private Feed feed;
	@ManyToMany( fetch = FetchType.EAGER )
	private Collection<Author> authors;

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

	public Feed getFeed() {
		return feed;
	}

	public void setFeed( Feed feed ) {
		this.feed = feed;
	}

	public Collection<Author> getAuthors() {
		return authors;
	}

	public void setAuthors( Collection<Author> authors ) {
		this.authors = authors;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FeedItem item = (FeedItem) o;

        if (authors != null ? !authors.equals(item.authors) : item.authors != null) return false;
        if (createdOn != null ? !createdOn.equals(item.createdOn) : item.createdOn != null) return false;
        if (!feed.equals(item.feed)) return false;
        if (!text.equals(item.text)) return false;
        if (!title.equals(item.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + feed.hashCode();
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        return result;
    }
}
