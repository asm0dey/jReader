package com.github.asm0dey.client.presenters;

import com.github.gwtbootstrap.client.ui.AccordionGroup;
import com.gwtplatform.mvp.client.UiHandlers;

/**
 * User: finkel
 * <p/>
 * Date: 22.03.13
 * <p/>
 * Time: 19:45
 */
public interface MainPageUiHandlers extends UiHandlers{
    void loadFeeds(Long value, AccordionGroup accordionGroup);

    void addCategory(String value);

    void fetchItems(Long feedId);
}
