package com.github.asm0dey.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * User: finkel
 * <p/>
 * Date: 25.03.13
 * <p/>
 * Time: 10:51
 */
public interface Images extends ClientBundle{
    @Source("logo.png") ImageResource logo();
    @Source("ajax-loader.gif") ImageResource loader();
}
