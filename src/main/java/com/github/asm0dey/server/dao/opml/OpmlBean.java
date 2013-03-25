package com.github.asm0dey.server.dao.opml;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
	@Attribute( name = "version" )
	private String version;
    @Element(name = "body") BodyBean bodyBean;

	public String getVersion() {
		return version;
	}

	public void setVersion( String version ) {
		this.version = version;
	}

	public HeadBean getHead() {
		return head;
	}

	public void setHead(HeadBean head ) {
		this.head = head;
	}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
