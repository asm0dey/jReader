package com.github.asm0dey.shared.opml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * User: finkel
 * <p/>
 * Date: 24.03.13
 * <p/>
 * Time: 11:18
 */
@Root( name = "opml" )
public class OpmlBean {
	@Element( name = "head" )
	HeadBean head;
	@Element( name = "body" )
	BodyBean bodyBean;
	@Attribute( name = "version" )
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion( String version ) {
		this.version = version;
	}

	public HeadBean getHead() {
		return head;
	}

	public void setHead( HeadBean head ) {
		this.head = head;
	}

    public BodyBean getBodyBean() {
        return bodyBean;
    }

    public void setBodyBean(BodyBean bodyBean) {
        this.bodyBean = bodyBean;
    }
}
