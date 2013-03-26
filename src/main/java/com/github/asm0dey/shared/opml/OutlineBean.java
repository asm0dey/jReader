package com.github.asm0dey.shared.opml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * User: finkel
 * <p/>
 * Date: 24.03.13
 * <p/>
 * Time: 11:36
 */
@Root
public class OutlineBean {
	@ElementList( entry = "outline", inline = true, empty = true, required = false )
	List<OutlineBean> outlineBeans;
	@Attribute( required = true )
	String text;
	@Attribute( required = false )
	String description, htmlUrl, language, title, version, xmlUrl, type;

	public String getXmlUrl() {
		return xmlUrl;
	}

	public void setXmlUrl( String xmlUrl ) {
		this.xmlUrl = xmlUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl( String htmlUrl ) {
		this.htmlUrl = htmlUrl;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage( String language ) {
		this.language = language;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion( String version ) {
		this.version = version;
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

	public List<OutlineBean> getOutlineBeans() {
		return outlineBeans;
	}

	public void setOutlineBeans( List<OutlineBean> outlineBeans ) {
		this.outlineBeans = outlineBeans;
	}

	public boolean isCategory() {
		return outlineBeans != null && !outlineBeans.isEmpty();
	}

	public String getType() {
		return type;
	}

	public void setType( String type ) {
		this.type = type;
	}
}
