package com.github.asm0dey.shared.domain;

import org.hibernate.annotations.Index;

import javax.persistence.*;

/**
 * User: finkel
 * <p/>
 * Date: 16.03.13
 * <p/>
 * Time: 16:51
 */
@Entity
public class Author extends AbstractPojo {
    @Index(name = "authot_email_index")
    private String email;
    @Column(nullable = false)
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
    public String toString() {
        return "Author{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
