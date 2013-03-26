package com.github.asm0dey.shared.opml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root( name = "head" )
public class HeadBean {
	@Element( required = false )
	private String title;
	@Element( required = false )
	private String dateCreated;
	@Element( required = false )
	private String dateModified;
	@Element( required = false )
	private String ownerName;
	@Element( required = false )
	private String ownerEmail;
	@Element( required = false )
	private String ownerId;
	@Element( required = false )
	private String docs;

	public HeadBean() {
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated( String dateCreated ) {
		this.dateCreated = dateCreated;
	}

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified( String dateModified ) {
		this.dateModified = dateModified;
	}

	public String getDocs() {
		return docs;
	}

	public void setDocs( String docs ) {
		this.docs = docs;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail( String ownerEmail ) {
		this.ownerEmail = ownerEmail;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId( String ownerId ) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName( String ownerName ) {
		this.ownerName = ownerName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}

}