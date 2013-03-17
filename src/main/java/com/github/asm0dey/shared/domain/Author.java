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
@Table( uniqueConstraints = { @UniqueConstraint( columnNames = {"email"} ) } )
public class Author extends AbstractPojo {
    private String email;
    private String name;
    private String link;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        if (!super.equals(o)) return false;

        Author author = (Author) o;

        if (email != null ? !email.equals(author.email) : author.email != null) return false;
        if (link != null ? !link.equals(author.link) : author.link != null) return false;
        if (!name.equals(author.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
