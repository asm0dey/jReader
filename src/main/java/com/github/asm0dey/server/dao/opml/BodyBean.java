package com.github.asm0dey.server.dao.opml;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * User: finkel
 * <p/>
 * Date: 24.03.13
 * <p/>
 * Time: 11:35
 */
@Root(name = "body")
public class BodyBean {
    @ElementList(entry = "outline",inline = true,empty = false)
    List<OutlineBean> beans;

    public List<OutlineBean> getBeans() {
        return beans;
    }

    public void setBeans(List<OutlineBean> beans) {
        this.beans = beans;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
