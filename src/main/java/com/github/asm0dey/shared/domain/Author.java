package com.github.asm0dey.shared.domain;

import javax.persistence.*;
import java.util.List;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 16:51
 */
@Entity
@Table( uniqueConstraints = { @UniqueConstraint( columnNames = "name" ) } )
public class Author extends AbstractPojo {
	@ManyToMany( targetEntity = FeedItem.class, mappedBy = "authors", fetch = FetchType.EAGER )
	private List<FeedItem> items;
	private String name;

	public List<FeedItem> getItems() {
		return items;
	}

	public void setItems( List<FeedItem> items ) {
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        if (!super.equals(o)) return false;

        Author author = (Author) o;

        if (items != null ? !items.equals(author.items) : author.items != null) return false;
        if (!name.equals(author.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (items != null ? items.hashCode() : 0);
        result = 31 * result + name.hashCode();
        return result;
    }
}
